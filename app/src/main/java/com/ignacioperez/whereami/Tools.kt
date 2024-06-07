package com.ignacioperez.whereami

import androidx.compose.runtime.Composable
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

fun checkAccountExists(
    email: String,
    password: String,
    auth: FirebaseAuth,
    result: (Boolean) -> Unit
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
