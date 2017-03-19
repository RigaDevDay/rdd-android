package lv.rigadevday.android.ui.speakers

import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_speaker.*
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.model.other.SocialAccount
import lv.rigadevday.android.repository.model.speakers.Speaker
import lv.rigadevday.android.ui.EXTRA_SPEAKER_ID
import lv.rigadevday.android.ui.base.BaseActivity
import lv.rigadevday.android.ui.openWeb
import lv.rigadevday.android.utils.*

class SpeakerDialogActivity : BaseActivity() {

    override val layoutId = R.layout.activity_speaker

    override fun inject() {
        BaseApp.graph.inject(this)
    }

    override fun viewReady() {
        val speakerId = intent.extras.getInt(EXTRA_SPEAKER_ID, -1)

        speaker_background.setOnClickListener { finish() }
        speaker_close.setOnClickListener { finish() }

        repo.speaker(speakerId).subscribe(
            { renderSpeaker(it) },
            { _ ->
                speaker_name.showMessage(R.string.error_message)
                finish()
            }
        )
    }

    private fun renderSpeaker(speaker: Speaker) {
        speaker_photo.loadCircleAvatar(speaker.photoUrl)

        speaker_name.text = speaker.name
        speaker_title.text = speaker.title
        speaker_company.text = getString(R.string.speaker_company, speaker.company)

        speaker_bio.text = speaker.shortBio.fromHtml()

        if (speaker.socials.isEmpty()) {
            speaker_social_icons.hide()
        } else speaker.socials.forEach { social ->
            when (social.name.toLowerCase()) {
                "website" -> showSocialIcon(speaker_social_blog, social)
                "twitter" -> showSocialIcon(speaker_social_twitter, social)
                "linkedin" -> showSocialIcon(speaker_social_linkedin, social)
            }
        }
    }

    private fun showSocialIcon(button: ImageView, social: SocialAccount) {
        button.show()
        button.setOnClickListener { baseContext.openWeb(social.link) }
    }

}
