package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

data class AyahMoment(
    val arabic: String,
    val urdu: String,
    val arabicTafsir: String,
    val reference: String,
    val dateString: String,
    val shamelaUrl: String,
    val bookTitle: String
)

val InspiringIslamicQuotes = listOf(
    // 1. Surah Al-Qasas 28:50 (Exact Ayah from user screenshot)
    AyahMoment(
        arabic = "فَإِنْ لَمْ يَسْتَجِيبُوا لَكَ فَاعْلَمْ أَنَّمَا يَتَّبِعُونَ أَهْوَاءَهُمْ ۚ وَمَنْ أَضَلُّ مِمَّنِ اتَّبَعَ هَوَاهُ بِغَيْرِ هُدًى مِنَ اللَّهِ ۚ إِنَّ اللَّهَ لَا يَهْدِي الْقَوْمَ الظَّالِمِينَ",
        urdu = "پھر اگر وہ تیری بات قبول نہ کریں ، پھر تو جان لے ، کہ کچھ بھی شک نہیں کہ وہ اپنی خواہشوں کے پیچھے چلتے ہیں ، اور اس سے زیادہ گم راہ کون ہوگا ، جو اللہ کی ہدایت کے بغیر اپنی خواہش کے پیچھے چلے ، بے شک اللہ ظالموں کو ہدایت نہیں دیتا۔",
        arabicTafsir = "تفسير ابن كثير: أي فإن لم يجيبوك يا محمد إلى ما دعوتهم إليه من الحق فاعلم أنهم متبعون لأهوائهم بغير برهان ولا دليل.",
        reference = "SURAH: 28 القصص AYAH: 50",
        dateString = "جمعہ، 30 اکتوبر 2026",
        shamelaUrl = "https://shamela.ws/book/23604/3070",
        bookTitle = "تفسير القرآن العظيم (ابن كثير)"
    ),
    // 2. Ayat al-Kursi (Surah Al-Baqarah 2:255)
    AyahMoment(
        arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ",
        urdu = "اللہ، اس کے سوا کوئی عبادت کے لائق نہیں، وہ ہمیشہ زندہ اور سب کو سنبھالنے والا ہے، نہ اسے اونگھ آتی ہے نہ نیند، اسی کا ہے جو کچھ آسمانوں اور زمین میں ہے۔",
        arabicTafsir = "تفسير ابن كثير: هذه آية الكرسي ولها شأن عظيم وهي أعظم آية في كتاب الله عز وجل المشتملة على أصول التوحيد.",
        reference = "SURAH: 2 البقرة AYAH: 255",
        dateString = "سید الآیات - آية الكرسي",
        shamelaUrl = "https://shamela.ws/book/23604/320",
        bookTitle = "تفسير آية الكرسي (ابن كثير)"
    ),
    // 3. Sahih al-Bukhari (Hadith 1 - Niyyah)
    AyahMoment(
        arabic = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى، فَمَنْ كَانَتْ هِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ فَهِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ",
        urdu = "اعمال کا دارومدار نیتوں پر ہے، اور ہر انسان کے لیے وہی ہے جس کی اس نے نیت کی، پس جس کی ہجرت اللہ اور اس کے رسول کے لیے ہو اس کی ہجرت اللہ اور اس کے رسول کے لیے ہے۔",
        arabicTafsir = "صحيح البخاري: باب كيف كان بدء الوحي - حديث عظيم يدور عليه قطب رحى الإسلام وميزان الأعمال الباطنة.",
        reference = "صحيح البخاري: حديث 1",
        dateString = "حدیث اخلاص و نیت",
        shamelaUrl = "https://shamela.ws/book/1680/1",
        bookTitle = "صحيح البخاري"
    ),
    // 4. Sahih Muslim (Hadith on Seeking Knowledge)
    AyahMoment(
        arabic = "مَنْ سَلَكَ طَرِيقًا يَلْتَمِسُ فِيهِ عِلْمًا، سَهَّلَ اللهُ لَهُ بِهِ طَرِيقًا إِلَى الْجَنَّةِ، وَمَا اجْتَمَعَ قَوْمٌ فِي بَيْتٍ مِنْ بُيُوتِ اللهِ يَتْلُونَ كِتَابَ اللهِ إِلَّا نَزَلَتْ عَلَيْهِمُ السَّكِينَةُ",
        urdu = "جو شخص علمِ دین حاصل کرنے کے راستے پر چلا، اللہ تعالیٰ اس کے لیے جنت کی راہ آسان فرما دیتا ہے، اور جو لوگ اللہ کے گھر میں بیٹھ کر کتاب اللہ کی تلاوت کرتے ہیں ان پر سکینت نازل ہوتی ہے۔",
        arabicTafsir = "صحيح مسلم: فضل الاجتماع على تلاوة القرآن وطلب العلم الشرعي النافع المقرب إلى رضوان الله.",
        reference = "صحيح مسلم: حديث 2699",
        dateString = "فضل طلب العلم الشرعي",
        shamelaUrl = "https://shamela.ws/book/1727/6803",
        bookTitle = "صحيح مسلم"
    ),
    // 5. Imam Abu Hanifah on Fiqh Knowledge
    AyahMoment(
        arabic = "رَأْيُنَا هَذَا أَحْسَنُ مَا قَدَرْنَا عَلَيْهِ، فَمَنْ جَاءَنَا بِأَحْسَنَ مِنْهُ فَهُوَ أَوْلَى بِالصَّوَابِ",
        urdu = "ہماری یہ فقہی رائے وہ بہترین بات ہے جس پر ہم قدرت پا سکے، پس جو شخص ہم سے بہتر دلیل اور رائے لے آئے تو وہ حق و ثواب کے زیادہ قریب ہے۔",
        arabicTafsir = "من درر الإمام الأعظم أبي حنيفة النعمان رحمه الله تعالى في الإنصاف والتواضع العلمي وبذل الجهد في استنباط الأحكام.",
        reference = "رد المحتار (مقدمة الفقه الحنفي)",
        dateString = "حکمت و تواضع فقہ حنفی",
        shamelaUrl = "https://shamela.ws/book/1208/1",
        bookTitle = "رد المحتار على الدر المختار"
    )
)

@Composable
fun AyahOfTheMomentCard(
    lang: AppLanguage,
    onReadAyahInShamela: (BookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentIndex by remember { mutableIntStateOf(0) }
    var isBookmarked by remember { mutableStateOf(false) }

    val currentAyah = InspiringIslamicQuotes[currentIndex % InspiringIslamicQuotes.size]

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0x38163524),
                        Color(0x220D1F16),
                        Color(0x2C10281D)
                    )
                )
            )
            .testTag("ayah_moment_card")
    ) {
        // Top Specular Highlight Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x604ADE80),
                            Color(0x99FFFFFF),
                            Color(0x604ADE80),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Pill: AYAH OF THE MOMENT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x3322C55E),
                    border = BorderStroke(1.dp, Color(0x664ADE80))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = Strings.ayahOfTheMomentHeader(lang),
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = LuminousGreenText,
                            letterSpacing = 1.sp
                        )
                    }
                }

                // Shuffle / Next quote button
                IconButton(
                    onClick = {
                        currentIndex = (currentIndex + 1) % InspiringIslamicQuotes.size
                    },
                    modifier = Modifier.size(34.dp).testTag("ayah_shuffle_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Next Ayah",
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            AnimatedContent(
                targetState = currentAyah,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ayah_text_transition"
            ) { ayah ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Arabic Quranic / Hadith Text in Glowing Emerald Green with full diacritics
                    Text(
                        text = ayah.arabic,
                        fontFamily = AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        lineHeight = 38.sp,
                        color = LuminousGreenText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Translation or Explanation:
                    // If Urdu: Jameel Noori Nastaleeq script in white
                    // If Arabic: Classical Tafsir explanation in Amiri script
                    if (lang == AppLanguage.URDU) {
                        Text(
                            text = ayah.urdu,
                            fontFamily = JameelNooriFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            lineHeight = 32.sp,
                            color = TextWhitePrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
                        )
                    } else {
                        Text(
                            text = ayah.arabicTafsir,
                            fontFamily = AmiriFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            lineHeight = 26.sp,
                            color = Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reference in Glowing Green
                    Text(
                        text = ayah.reference,
                        fontFamily = AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = RadiantGreenPrimary,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Date String
                    Text(
                        text = ayah.dateString,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                        color = TextMutedGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Icons Row: Read in Shamila, Copy, Share, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Read in Shamila Button
                Button(
                    onClick = {
                        val bookEntity = BookEntity(
                            title = currentAyah.bookTitle,
                            author = "المكتبة الشاملة",
                            category = "Tafsir",
                            url = currentAyah.shamelaUrl,
                            notes = currentAyah.arabicTafsir,
                            isFavorite = true
                        )
                        onReadAyahInShamela(bookEntity)
                    },
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x3522C55E)
                    ),
                    border = BorderStroke(1.dp, Color(0x804ADE80)),
                    modifier = Modifier.testTag("read_ayah_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.readTafsir(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = LuminousGreenText
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Copy to Clipboard
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val copyContent = "${currentAyah.arabic}\n\n${if (lang == AppLanguage.URDU) currentAyah.urdu else currentAyah.arabicTafsir}\n[${currentAyah.reference}]"
                        clipboard.setPrimaryClip(ClipData.newPlainText("Ayah", copyContent))
                        Toast.makeText(context, Strings.ayahCopied(lang), Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x221E3B2A))
                        .testTag("copy_ayah_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Share
                IconButton(
                    onClick = {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${currentAyah.arabic}\n\n${if (lang == AppLanguage.URDU) currentAyah.urdu else currentAyah.arabicTafsir}\n\n[${currentAyah.reference}]\n${currentAyah.shamelaUrl}"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(intent, currentAyah.reference))
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x221E3B2A))
                        .testTag("share_ayah_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Bookmark
                IconButton(
                    onClick = {
                        isBookmarked = !isBookmarked
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isBookmarked) Color(0x4022C55E) else Color(0x221E3B2A))
                        .testTag("bookmark_ayah_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) RadiantGreenPrimary else TextMutedGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
