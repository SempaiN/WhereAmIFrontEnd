package com.ignacioperez.whereami

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ignacioperez.whereami.models.Item
import com.ignacioperez.whereami.models.StatResponse
import com.ignacioperez.whereami.models.StatsModifiedCharacter

fun checkPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    return regex.matches(password)

}

fun checkEmail(email: String): Boolean {
    val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    return regex.matches(email)
}

fun checkAccountExistsFirebase(
    email: String,
    password: String,
    auth: FirebaseAuth,
    result: (Boolean) -> Unit,
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            result(true)
        }
        .addOnFailureListener {
            result(false)
        }
}


fun checkDoubleActivatedItem(newItem: Item, listItems: List<Item>): Boolean {
    for (item in listItems) {
        if ((newItem.charges == -1 || newItem.charges >= 1) and (item.charges == -1 || item.charges >= 1)) {
            return true
        }
    }
    return false
}

val auth = Firebase.auth

fun checkStatsChanges(
    statsResponse: StatResponse,
    statsModifiedCharacter: StatsModifiedCharacter
): Boolean {
    for (stat in statsResponse) {
        var foundMatch = false
        for (statModified in statsModifiedCharacter) {
            if (statModified.total_value == stat.value) {
                foundMatch = true
                break
            }
        }
        if (!foundMatch) {
            return false
        }
    }
    return true
}

val healthRegex = Regex("^(0|[1-9]|1[0-2])?(\\.\\d?)?\$")
val damageRegex = Regex("^(1|[1-9])?(\\.0|\\.5)?\$")
val speedRegex = Regex("^(1(\\.[0-5]?)?)?\$")
val rangeRegex = Regex("^(7|8|9|10|11|12|13|14)?(\\.[0-9]?)?\$")
val shotSpeedRegex = Regex("^(1(\\.[0-9]?)?|2\\.0)?\$")
val luckRegex = Regex("^(-2\\.0|-1(\\.[0-9]?)?|0(\\.[0-9]?)?|1(\\.[0-4]?)?)?\$")
val tearsRegex = Regex("^(1(\\.[0-9]?)?|2(\\.[0-9]?)?|3\\.0)?\$")

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
    }
    return false
}

val listImagesAppearances =
    listOf(
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/1/11/Collectible_20_20_appearance.png/revision/latest?cb=20230613191329",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/f/f1/Collectible_Cricket%27s_Head_appearance.png/revision/latest?cb=20210821114616",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/a/ae/Collectible_Goat_Head_appearance.png/revision/latest?cb=20210821120233",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/8/8e/Collectible_Ipecac_appearance.png/revision/latest?cb=20210821090030",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/5/56/Collectible_Mutant_Spider_appearance.png/revision/latest?cb=20210821115848",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/9/93/Collectible_Polyphemus_appearance.png/revision/latest?cb=20210821075814",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/6/6b/Collectible_Pyromaniac_appearance.png/revision/latest?cb=20210821075342",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/e/ef/Collectible_Steven_appearance.png/revision/latest?cb=20210821095448",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/f/f4/Collectible_Chaos_appearance.png/revision/latest?cb=20210821170215",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/f/fc/Collectible_Head_of_the_Keeper_appearance.png/revision/latest?cb=20210821184028",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/b/bb/Collectible_Tech_X_appearance.png/revision/latest?cb=20210821165913",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/4/44/Collectible_Stapler_appearance.png/revision/latest?cb=20210824145951",
        "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/1/1b/Collectible_Godhead_appearance.png/revision/latest?cb=20210821085851"
    )

const val transitionImageIsaac = "https://static.wikia.nocookie.net/bindingofisaacre_gamepedia/images/d/d2/Transition_Isaac.png/revision/latest?cb=20210826044406"