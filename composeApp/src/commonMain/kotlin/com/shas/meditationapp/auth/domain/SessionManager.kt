import com.russhwolf.settings.Settings
import com.shas.meditationapp.auth.data.GoogleUser

class SessionManager(
    private val settings: Settings
) {

    companion object {

        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_GUEST_USER = "is_guest_user"

        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PROFILE = "user_profile"
    }

    fun saveIsGuestUser() {
        settings.putBoolean(KEY_IS_GUEST_USER,true)
    }

    fun saveUser(user: GoogleUser) {

        settings.putBoolean(KEY_IS_LOGGED_IN, true)

        settings.putString(KEY_USER_ID, user.id)
        settings.putString(KEY_USER_NAME, user.name ?: "")
        settings.putString(KEY_USER_EMAIL, user.email ?: "")
        settings.putString(KEY_USER_PROFILE, user.profileUrl ?: "")
    }

    fun getUser(): GoogleUser? {

        val isLoggedIn =
            settings.getBoolean(KEY_IS_LOGGED_IN, false)

        if (!isLoggedIn) return null

        return GoogleUser(
            id = settings.getString(KEY_USER_ID, ""),
            name = settings.getString(KEY_USER_NAME, ""),
            email = settings.getString(KEY_USER_EMAIL, ""),
            profileUrl = settings.getString(KEY_USER_PROFILE, "")
        )
    }

    fun isGuestUser() : Boolean {
        return settings.getBoolean(KEY_IS_GUEST_USER,false)
    }

    fun logout() {
        settings.clear()
    }
}