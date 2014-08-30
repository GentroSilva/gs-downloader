/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.twitch;

import com.lexicalscope.jewel.cli.Option;

/**
 *
 * @author bdickie
 */
public interface AppCli {

    @Option( shortName = { "a", "i" }, pattern = "(?:http://www.twitch.tv/[^/]+/[abc]/)?\\d+" )
    public String getId();

    @Option( shortName = "d" )
    public String getDescription();

    @Option( shortName = "p" )
    public boolean isPartial();

    @Option( shortName = "r" )
    public boolean isRun();

    @Option( shortName = "h", helpRequest = true )
    public boolean getHelp();

}
