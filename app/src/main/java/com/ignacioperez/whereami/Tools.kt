package com.ignacioperez.whereami

import androidx.compose.runtime.Composable
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
