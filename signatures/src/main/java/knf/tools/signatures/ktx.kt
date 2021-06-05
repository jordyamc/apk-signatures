package knf.tools.signatures

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

open class SignatureInfo(val signatures: List<Signature> = emptyList())

data class Signature(val encoded: String, val hashBase64: String)

class SignatureInfoError(val exception: Exception): SignatureInfo()

fun Context.getSignatures(pkg: String = packageName, encoder: String = "SHA"): SignatureInfo {
    return try {
        val info = packageManager.getPackageInfo(pkg, PackageManager.GET_SIGNATURES)
        val signatures = mutableListOf<Signature>()
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance(encoder)
            md.update(signature.toByteArray())
            val digest = md.digest()
            val toRet = StringBuilder()
            for (i in digest.indices) {
                if (i != 0) toRet.append(":")
                val b = digest[i].toInt() and 0xff
                val hex = Integer.toHexString(b)
                if (hex.length == 1) toRet.append("0")
                toRet.append(hex)
            }
            signatures.add(Signature(toRet.toString(), Base64.encodeToString(digest, Base64.DEFAULT)))
        }
        SignatureInfo(signatures)
    } catch (e: Exception) {
        SignatureInfoError(e)
    }
}