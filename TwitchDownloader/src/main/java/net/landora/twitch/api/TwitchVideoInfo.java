/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.twitch.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 *
 * @author bdickie
 */
@JsonIgnoreProperties( { "restrictions", "vod_ad_frequency", "vod_ad_length" } )
public class TwitchVideoInfo {

    private int startOffset;
    private int endOffset;
    private int playOffset;
    private String incrementViewCountUrl;
    private String path;
    private String broadcasterSoftware;
    private String channel;
    private String preview;
    private String previewSmall;
    private Map<String, Chunk[]> chunks;
    private String apiId;

    public TwitchVideoInfo() {
    }

    @JsonProperty( "start_offset" )
    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset( int startOffset ) {
        this.startOffset = startOffset;
    }

    @JsonProperty( "end_offset" )
    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset( int endOffset ) {
        this.endOffset = endOffset;
    }

    @JsonProperty( "play_offset" )
    public int getPlayOffset() {
        return playOffset;
    }

    public void setPlayOffset( int playOffset ) {
        this.playOffset = playOffset;
    }

    @JsonProperty( "increment_view_count_url" )
    public String getIncrementViewCountUrl() {
        return incrementViewCountUrl;
    }

    public void setIncrementViewCountUrl( String incrementViewCountUrl ) {
        this.incrementViewCountUrl = incrementViewCountUrl;
    }

    @JsonProperty( "path" )
    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    @JsonProperty( "broadcaster_software" )
    public String getBroadcasterSoftware() {
        return broadcasterSoftware;
    }

    public void setBroadcasterSoftware( String broadcasterSoftware ) {
        this.broadcasterSoftware = broadcasterSoftware;
    }

    @JsonProperty( "channel" )
    public String getChannel() {
        return channel;
    }

    public void setChannel( String channel ) {
        this.channel = channel;
    }

    @JsonProperty( "preview" )
    public String getPreview() {
        return preview;
    }

    public void setPreview( String preview ) {
        this.preview = preview;
    }

    @JsonProperty( "preview_small" )
    public String getPreviewSmall() {
        return previewSmall;
    }

    public void setPreviewSmall( String previewSmall ) {
        this.previewSmall = previewSmall;
    }

    public Map<String, Chunk[]> getChunks() {
        return chunks;
    }

    public void setChunks( Map<String, Chunk[]> chunks ) {
        this.chunks = chunks;
    }

    @JsonProperty( "api_id" )
    public String getApiId() {
        return apiId;
    }

    public void setApiId( String apiId ) {
        this.apiId = apiId;
    }

}
