package com.reviro.data.local.prefs

interface AuthLocaleSource {

    var isFirstLaunch: Boolean

    var latitude: String?

    var longitude: String?
}