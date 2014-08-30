/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.twitch.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author bdickie
 */
@JsonIgnoreProperties({"upkeep"})
public class Chunk {

    private String url;
    private String cdmUrl;
    private int length;
    private String vodCountUrl;

    public Chunk() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength( int length ) {
        this.length = length;
    }

    @JsonProperty( "vod_count_url" )
    public String getVodCountUrl() {
        return vodCountUrl;
    }

    public void setVodCountUrl( String vodCountUrl ) {
        this.vodCountUrl = vodCountUrl;
    }

    @JsonProperty( "cdn_url" )
    public String getCdmUrl() {
        return cdmUrl;
    }

    public void setCdmUrl( String cdmUrl ) {
        this.cdmUrl = cdmUrl;
    }

}
