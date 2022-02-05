package org.spotify.importer.dto.music

class Track {
    var title: String
    var artist: String
    var album: String? = null
    var idSpotify: String? = null

    constructor(title: String, artist: String) {
        this.title = title
        this.artist = artist
    }

    constructor(title: String, artist: String, album: String?) {
        this.title = title
        this.artist = artist
        this.album = album
    }
}