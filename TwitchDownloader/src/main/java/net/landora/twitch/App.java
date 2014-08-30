package net.landora.twitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.landora.twitch.api.Chunk;
import net.landora.twitch.api.TwitchVideoInfo;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args ) throws Exception {
        
        AppCli cli;
        
        try {
            cli = CliFactory.parseArguments( AppCli.class, args );
        } catch ( ArgumentValidationException e ) {
            System.err.println( e.getMessage() );
            System.exit( -1 );
            return;
        }
        
        String videoId = cli.getId().replaceFirst( "http://www.twitch.tv/[^/]+/[abc]/", "" );
        String outputName = cli.getDescription();
        int startHours = 0;
        int startMins = 0;
        
        int endHours = -1;
        int endMins = -1;

        // End of Config
        URL url = new URL( "https://api.twitch.tv/api/videos/a" + videoId );
        ObjectMapper mapper = new ObjectMapper();
        TwitchVideoInfo readValue = mapper.readValue( url, TwitchVideoInfo.class );
        int skipTotal = ( startHours * 60 + startMins ) * 60;
        Chunk[] chunks = readValue.getChunks().get( "live" );
        List<String> filenames = new ArrayList<String>();
        int skipLeft = skipTotal;
        
        int endLeft = ( endHours < 0 || endMins < 0 ? Integer.MAX_VALUE : ( endHours * 60 + endMins ) * 60 );
        
        String mp4Out = String.format( "%s - %s.mp4", videoId, outputName );
        String mkvOut = String.format( "%s - %s.mkv", videoId, outputName );
        
        PrintStream out;
        File tempFile = null;
        
        if ( cli.isRun() ) {
            tempFile = File.createTempFile( "TwitchDownload", ".sh" );
            tempFile.deleteOnExit();
            out = new PrintStream( tempFile );
        } else
            out = System.out;
        
        out.println( "if [ ! -f \"" + mp4Out + "\" ]\nthen" );
        
        for ( Chunk chunk : chunks ) {
            skipLeft -= chunk.getLength();
            endLeft -= chunk.getLength();
            if ( skipLeft > 0 || endLeft + chunk.getLength() < 0 )
                continue;
            String chunkUrl = chunk.getUrl();
            if ( chunk.getCdmUrl() != null )
                chunkUrl = chunk.getCdmUrl();
            String filename = chunkUrl.substring( chunkUrl.lastIndexOf( '/' ) + 1 ).replaceAll( ".flv$", "" );
            out.println( "#  " + filename );
            
            out.println( "    if [ \\( ! -f \"" + filename + ".flv\" \\) -o \\( -f \"" + filename + ".flv.st\" \\) ]\n      then" );
            out.println( "      axel -o \"" + filename + ".flv\" \"" + chunkUrl + "\"" );
            out.println( "    fi" );
            out.println( "    if [ \\( ! -f \"" + filename + ".flv\" \\) -o \\( -f \"" + filename + ".flv.st\" \\) ]\n      then" );
            out.println( "      # Download Failed" );
            out.println( "      exit" );
            out.println( "    fi" );
            
            filenames.add( filename );
        }
        
        if ( !cli.isPartial() ) {
            
            StringBuilder cmd = new StringBuilder();
            cmd.append( "  ffmpeg -f concat -i <( " );
            
            for ( int i = 0; i < filenames.size(); i++ ) {
                String filename = filenames.get( i );
                if ( i > 0 )
                    cmd.append( " ; " );
                
                cmd.append( "echo \"file '${PWD}/" );
                cmd.append( filename );
                cmd.append( ".flv'\" " );
            }
            
            cmd.append( " ) -vcodec copy -acodec copy \"" );
            cmd.append( mp4Out );
            cmd.append( "\"" );
            out.println( cmd.toString() );
            
            out.println( "  if [ $? -ne 0 ]\n    then" );
            out.println( "    rm \"" + mp4Out + "\"" );
            out.println( "    exit" );
            out.println( "  fi" );
            
            for ( String filename : filenames )
                out.println( "  rm \"" + filename + ".flv\"" );
        }
        out.println( "fi" );
        
        if ( cli.isRun() ) {
            out.close();
            
            ProcessBuilder pb = new ProcessBuilder( "/bin/bash", tempFile.getAbsolutePath() );
            pb.redirectError( ProcessBuilder.Redirect.INHERIT );
            pb.redirectOutput( ProcessBuilder.Redirect.INHERIT );
            pb.redirectInput( ProcessBuilder.Redirect.INHERIT );
            Process p = pb.start();
            System.exit( p.waitFor() );
        }
        
    }
}
