package com.example.data

object DefaultData {
    val initialCategories = listOf(
        CategoryEntity(
            id = 1,
            name = "فقہ حنفی",
            nameMm = "فقه حنفي",
            isDefault = true
        ),
        CategoryEntity(
            id = 2,
            name = "Tafsir",
            nameMm = "التفسير",
            isDefault = true
        ),
        CategoryEntity(
            id = 3,
            name = "Hadith",
            nameMm = "الحديث الشريف",
            isDefault = true
        ),
        CategoryEntity(
            id = 4,
            name = "Fiqh",
            nameMm = "الفقه العام",
            isDefault = true
        ),
        CategoryEntity(
            id = 5,
            name = "Aqidah",
            nameMm = "العقيدة",
            isDefault = true
        ),
        CategoryEntity(
            id = 6,
            name = "Seerah & History",
            nameMm = "السيرة والتاريخ",
            isDefault = true
        ),
        CategoryEntity(
            id = 7,
            name = "Arabic Language",
            nameMm = "اللغة العربية",
            isDefault = true
        ),
        CategoryEntity(
            id = 8,
            name = "AI & الفتوى",
            nameMm = "الذكاء الاصطناعي",
            isDefault = true
        )
    )

    val initialBooks = listOf(
        // ==========================================
        // فقہ حنفی (Hanafi Fiqh Masterworks - Verified Shamela links)
        // ==========================================
        BookEntity(
            title = "رد المحتار على الدر المختار (حاشية ابن عابدين)",
            author = "محمد أمين بن عمر (ابن عابدين الدمشقي)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21613",
            notes = "فقہ حنفی کی سب سے مستند اور معتمد علیہ کتاب (فتاویٰ شامی)",
            isFavorite = true
        ),
        BookEntity(
            title = "الهداية في شرح بداية المبتدي",
            author = "علي بن أبي بكر بن عبد الجلیل المرغيناني",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/11820",
            notes = "فقہ حنفی کی مشہور ترین اور مرکزی درسی کتاب",
            isFavorite = true
        ),
        BookEntity(
            title = "المبسوط للسرخسي",
            author = "شمس الأئمة محمد بن أحمد بن أبي سهل السرخسي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/5423",
            notes = "امام سرخسی کی ۳۰ جلدوں پر مشتمل عظیم الشان فقہی انسائیکلوپیڈیا",
            isFavorite = true
        ),
        BookEntity(
            title = "بدائع الصنائع في ترتيب الشرائع",
            author = "علاء الدين أبو بكر بن مسعود الكاساني (الملقب بملك العلماء)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/8183",
            notes = "حسن ترتیب اور عمدہ ابواب بندی میں بے مثال حنفی کتاب",
            isFavorite = true
        ),
        BookEntity(
            title = "الفتاوى الهندية (الفتاوى العالمكيرية)",
            author = "جماعة من كبار علماء الهند برئاسة الشيخ نظام الدين البلخي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21640",
            notes = "شاہ اورنگزیب عالمگیر کے دور میں علمائے ہند کا تیار کردہ فتاویٰ",
            isFavorite = true
        ),
        BookEntity(
            title = "مختصر القدوري في الفقه الحنفي",
            author = "أبو الحسين أحمد بن محمد بن أحمد القدوري",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/124336",
            notes = "فقہ حنفی کا بنیادی ترین اور متداول متن",
            isFavorite = true
        ),
        BookEntity(
            title = "فتح القدير للعاجز الفقير",
            author = "كمال الدين محمد بن عبد الواحد السيواسي (ابن الهمام)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21744",
            notes = "ہدایہ کی سب سے مفصل اور دلائل سے مزین شرح",
            isFavorite = true
        ),
        BookEntity(
            title = "البحر الرائق شرح كنز الدقائق",
            author = "زين الدين بن إبراهيم بن محمد (ابن نجيم المصري الحنفي)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/12227",
            notes = "کنز الدقائق کی عظیم اور گرانقدر شرح مع منحة الخالق وتكملة الطوري",
            isFavorite = true
        ),
        BookEntity(
            title = "كنز الدقائق",
            author = "أبو البركات عبد الله بن أحمد بن محمود النسفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/14262",
            notes = "فقہ حنفی کے متون اربعہ میں سے اہم ترین متن",
            isFavorite = true
        ),
        BookEntity(
            title = "تبيين الحقائق شرح كنز الدقائق",
            author = "فخر الدين عثمان بن علي الزيلعي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/23023",
            notes = "علامہ زيلعي کی مشہور اور متداول شرح مع حاشية الشلبي"
        ),
        BookEntity(
            title = "نور الإيضاح ونجاة الأرواح",
            author = "حسن بن عمار بن علي الشرنبلالي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/12117",
            notes = "عبادات (نماز، روزہ، زکوٰۃ، حج) سے متعلق فقہ حنفی کا بنیادی متن",
            isFavorite = true
        ),
        BookEntity(
            title = "مراقي الفلاح شرح نور الإيضاح",
            author = "حسن بن عمار بن علي الشرنبلالي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/412",
            notes = "نور الایضاح کی مفصل اور مستند شرح"
        ),
        BookEntity(
            title = "حاشية الطحطاوي على مراقي الفلاح",
            author = "أحمد بن محمد بن إسماعيل الطحطاوي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/11993",
            notes = "علامہ طحطاوی کا معتمد اور مستند حاشیہ"
        ),
        BookEntity(
            title = "الاختيار لتعليل المختار",
            author = "عبد الله بن محمود بن مودود الموصلي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/1066",
            notes = "دلائل و براہین کے ساتھ فقہ حنفی کی شاندار درسی کتاب",
            isFavorite = true
        ),
        BookEntity(
            title = "الدر المختار شرح تنوير الأبصار",
            author = "محمد بن علي بن محمد الحصكفي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/14250",
            notes = "تنویر الابصار کی جامع اور دقیق ترین شرح"
        ),
        BookEntity(
            title = "تحفة الفقهاء",
            author = "علاء الدين محمد بن أحمد السمرقندي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/7691",
            notes = "مختصر القدوری کے بعد ائمہ احناف کی اہم ترین مرجع کتاب"
        ),
        BookEntity(
            title = "الجوهرة النيرة على مختصر القدوري",
            author = "أبو بكر بن علي بن محمد الحدادي العبادي الزبيدي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21596",
            notes = "مختصر القدوری کی آسان اور سہل ترین شرح"
        ),
        BookEntity(
            title = "المحيط البرهاني في الفقه النعماني",
            author = "برهان الدين محمود بن أحمد بن الصدر الشهيد (ابن مازة)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/10875",
            notes = "فقہ حنفی کا جامع اور وسیع ذخیرہ"
        ),
        BookEntity(
            title = "اللباب في شرح الكتاب",
            author = "عبد الغني بن طالب بن حمادة الميداني الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21496",
            notes = "قدوری کی سب سے مقبول اور عام فہم درسی شرح",
            isFavorite = true
        ),
        BookEntity(
            title = "درر الحكام شرح غرر الأحكام",
            author = "محمد بن فرامرز الشهير بملا خسرو الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/11446",
            notes = "عثمانی دور کی مایہ ناز حنفی کتاب"
        ),
        BookEntity(
            title = "مجمع الأنهر في شرح ملتقى الأبحر",
            author = "عبد الرحمن بن محمد بن سليمان (شيخي زاده)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21644",
            notes = "ملتقى الأبحر کی مشہور و مستند ترین شرح"
        ),
        BookEntity(
            title = "ملتقى الأبحر",
            author = "إبراهيم بن محمد بن إبراهيم الحلبي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/6185",
            notes = "متون اربعہ کا خلاصہ اور قانونی مرجع"
        ),
        BookEntity(
            title = "البناية شرح الهداية",
            author = "بدر الدين أبو محمد محمود بن أحمد العيني الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/427",
            notes = "ہدایہ کی عظیم الشان مبسوط شرح مع تخریج احادیث"
        ),
        BookEntity(
            title = "العناية شرح الهداية",
            author = "محمد بن محمد بن محمود البابرتي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/9403",
            notes = "ہدایہ کے باریک اور دقیق نکات کی حل کنندہ شرح"
        ),
        BookEntity(
            title = "النهر الفائق شرح كنز الدقائق",
            author = "سراج الدين عمر بن إبراهيم بن نجيم الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/13620",
            notes = "بحر الرائق کے مصنف کے بھائی علامہ عمر بن نجیم کی شرح"
        ),
        BookEntity(
            title = "الجامع الصغير مع النافع الكبير",
            author = "الإمام محمد بن الحسن الشيباني / اللكنوي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/6160",
            notes = "ظاہر الرواية کی چھ بنیادی کتابوں میں سے اہم ترین کتاب",
            isFavorite = true
        ),
        BookEntity(
            title = "الجامع الكبير",
            author = "الإمام محمد بن الحسن الشيباني",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/1157",
            notes = "ظاہر الرواية کی گرانقدر تصنیف"
        ),
        BookEntity(
            title = "الأصل المعروف بالمبسوط",
            author = "الإمام محمد بن الحسن الشيباني",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/14285",
            notes = "فقہ حنفی کی ام الکتاب (ظاہر الرواية)"
        ),
        BookEntity(
            title = "كتاب الخراج لأبي يوسف",
            author = "الإمام القاضي أبو يوسف يعقوب بن إبراهيم الأنصاري",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/26333",
            notes = "اسلامی مالیات اور خراج پر امام ابو حنیفہ کے ارشد شاگرد کی تصنیف",
            isFavorite = true
        ),
        BookEntity(
            title = "شرح مختصر الطحاوي للجصاص",
            author = "الإمام أبو بكر أحمد بن علي الرازي الجصاص الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/16546",
            notes = "امام طحاوی کے فقہی متن پر امام جصاص کی مستند شرح"
        ),
        BookEntity(
            title = "شرح معاني الآثار للطحاوي",
            author = "الإمام أبو جعفر أحمد بن محمد الطحاوي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21108",
            notes = "احادیث احکام اور فقہ حنفی کے تفصیلی دلائل کا ذخیرہ",
            isFavorite = true
        ),
        BookEntity(
            title = "العقود الدرية في تنقيح الفتاوى الحامدية",
            author = "محمد أمين بن عمر (ابن عابدين الدمشقي)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/21687",
            notes = "علامہ ابن عابدین کا نہایت گرانقدر فتاویٰ"
        ),
        BookEntity(
            title = "الحجة على أهل المدينة",
            author = "الإمام محمد بن الحسن الشيباني",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/6161",
            notes = "امام محمد کا اہل مدینہ کے ساتھ مناظره اور فقہی دلائل"
        ),
        BookEntity(
            title = "تحفة الملوك في فقه مذهب أبي حنيفة",
            author = "محمد بن أبي بكر بن عبد القادر الرازي الحنفي",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/6173",
            notes = "متقدمین کے اسلوب پر لکھا گیا جامع متن"
        ),
        BookEntity(
            title = "فتح باب العناية بشرح النقاية",
            author = "علي بن سلطان محمد القاري (ملا علي القاري)",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/1141",
            notes = "ملا علی قاری حنفی کی فقہی شرح"
        ),
        BookEntity(
            title = "بداية المبتدي",
            author = "علي بن أبي بكر بن عبد الجلیل المرغيناني",
            category = "فقہ حنفی",
            url = "https://shamela.ws/book/6170",
            notes = "متن الہدایہ جس میں جامع الصغیر اور مختصر القدوری کو جمع کیا گیا"
        ),

        // ==========================================
        // Tafsir
        // ==========================================
        BookEntity(
            title = "تفسير ابن كثير (تفسير القرآن العظيم)",
            author = "ابن كثير (الإمام إسماعيل بن عمر)",
            category = "Tafsir",
            url = "https://shamela.ws/book/23604",
            notes = "تفسیر بالماثور میں سب سے زیادہ مقبول اور مستند تفسیر"
        ),
        BookEntity(
            title = "تفسير الطبري (جامع البيان عن تأويل آي القرآن)",
            author = "محمد بن جرير الطبري",
            category = "Tafsir",
            url = "https://shamela.ws/book/43",
            notes = "تمام تفاسیر کا بنیادی اور عظیم الشان مرجع"
        ),
        BookEntity(
            title = "تفسير القرطبي (الجامع لأحكام القرآن)",
            author = "أبو عبد الله محمد بن أحمد القرطبي",
            category = "Tafsir",
            url = "https://shamela.ws/book/23602",
            notes = "قرآنی احکام اور فقہی مسائل کی جامع تفسیر"
        ),
        BookEntity(
            title = "تفسير السعدي (تيسير الكريم الرحمن)",
            author = "عبد الرحمن بن ناصر السعدي",
            category = "Tafsir",
            url = "https://shamela.ws/book/23635",
            notes = "آسان اور عام فہم انداز میں لکھی گئی تفسیر"
        ),
        BookEntity(
            title = "تفسير الجلالين",
            author = "جلال الدين المحلي وجلال الدين السيوطي",
            category = "Tafsir",
            url = "https://shamela.ws/book/8381",
            notes = "مختصر اور جامع الفاظ میں قرآنی معانی کا بیان"
        ),

        // ==========================================
        // Hadith
        // ==========================================
        BookEntity(
            title = "صحيح البخاري (الجامع المسند الصحيح)",
            author = "محمد بن إسماعيل البخاري",
            category = "Hadith",
            url = "https://shamela.ws/book/1680",
            notes = "اصح الکتب بعد کتاب اللہ، امام بخاری کی مایہ ناز تصنیف",
            isFavorite = true
        ),
        BookEntity(
            title = "صحيح مسلم",
            author = "مسلم بن الحجاج النيسابوري",
            category = "Hadith",
            url = "https://shamela.ws/book/1727",
            notes = "جامع صحیح احادیث کا مستند ترین مجموعہ",
            isFavorite = true
        ),
        BookEntity(
            title = "سنن أبي داود",
            author = "أبو داود سليمان بن الأشعث السجستاني",
            category = "Hadith",
            url = "https://shamela.ws/book/1726",
            notes = "احکام اور فقہی احادیث کا اہم ترین مجموعہ"
        ),
        BookEntity(
            title = "جامع الترمذي (سنن الترمذي)",
            author = "محمد بن عيسى الترمذي",
            category = "Hadith",
            url = "https://shamela.ws/book/1728",
            notes = "احادیث کے ساتھ ائمہ کے فقہی مذاہب کا بیان"
        ),
        BookEntity(
            title = "رياض الصالحين من كلام سيد المرسلين",
            author = "يحيى بن شرف النووي",
            category = "Hadith",
            url = "https://shamela.ws/book/1697",
            notes = "روزمرہ زندگی کے آداب و اخلاق سے متعلق احادیث کا انتخاب",
            isFavorite = true
        ),
        BookEntity(
            title = "الأربعون النووية",
            author = "الإمام محيي الدين النووي",
            category = "Hadith",
            url = "https://shamela.ws/book/21800",
            notes = "دین کے بنیادی اصولوں پر مشتمل ۴۰ اہم احادیث"
        ),

        // ==========================================
        // Fiqh
        // ==========================================
        BookEntity(
            title = "المغني لابن قدامة",
            author = "موفق الدين عبد الله بن أحمد بن قدامة المقدسي",
            category = "Fiqh",
            url = "https://shamela.ws/book/8460",
            notes = "مقارن فقہ کا سب سے بڑا اور تفصیلی انسائیکلوپیڈیا"
        ),
        BookEntity(
            title = "بداية المجتهد ونهاية المقتصد",
            author = "ابن رشد الحفيد القرطبي",
            category = "Fiqh",
            url = "https://shamela.ws/book/752",
            notes = "فقہی ائمہ کے دلائل اور اختلاف کے اسباب کا فلسفہ"
        ),

        // ==========================================
        // Aqidah
        // ==========================================
        BookEntity(
            title = "كتاب التوحيد الذي هو حق الله على العبيد",
            author = "محمد بن عبد الوهاب",
            category = "Aqidah",
            url = "https://shamela.ws/book/8468",
            notes = "توحید اور عقائد پر مدلل کتاب"
        ),
        BookEntity(
            title = "العقيدة الواسطية",
            author = "شيخ الإسلام ابن تيمية",
            category = "Aqidah",
            url = "https://shamela.ws/book/7382",
            notes = "اہل سنت والجماعت کے بنیادی عقائد کا بیان"
        ),
        BookEntity(
            title = "العقيدة الطحاوية (متن الطحاوية)",
            author = "أبو جعفر أحمد بن محمد الطحاوي",
            category = "Aqidah",
            url = "https://shamela.ws/book/10543",
            notes = "امام ابو حنیفہ اور ان کے اصحاب کے عقائد پر متفقہ کتاب"
        ),

        // ==========================================
        // Seerah & History
        // ==========================================
        BookEntity(
            title = "الرحيق المختوم",
            author = "صفي الرحمن المباركفوري",
            category = "Seerah & History",
            url = "https://shamela.ws/book/1105",
            notes = "سیرت النبی ﷺ پر عالمی انعام یافتہ جامع و مستند کتاب",
            isFavorite = true
        ),
        BookEntity(
            title = "البداية والنهاية",
            author = "عماد الدين إسماعيل بن عمر بن كثير",
            category = "Seerah & History",
            url = "https://shamela.ws/book/4445",
            notes = "تخلیق کائنات سے لے کر قیامت تک کی تاریخ"
        ),
        BookEntity(
            title = "سير أعلام النبلاء",
            author = "شمس الدين أبو عبد الله الذهبي",
            category = "Seerah & History",
            url = "https://shamela.ws/book/2266",
            notes = "صحابہ، تابعین اور علمائے امت کے سوانح حیات کا خزانہ"
        ),

        // ==========================================
        // Arabic Language
        // ==========================================
        BookEntity(
            title = "الآجرومية في قواعد الإعراب",
            author = "ابن آجروم الصنهاجي",
            category = "Arabic Language",
            url = "https://shamela.ws/book/7680",
            notes = "عربی گرامر اور نحو کا سب سے بنیادی اور آسان رسالہ"
        ),
        BookEntity(
            title = "لسان العرب",
            author = "محمد بن مكرم بن منظور الأفريقي",
            category = "Arabic Language",
            url = "https://shamela.ws/book/1730",
            notes = "عربی زبان کی سب سے جامع اور مستند لغت"
        ),

        // ==========================================
        // الذكاء الاصطناعي والفتوى (AI & Online Fatwa)
        // ==========================================
        BookEntity(
            title = "المكتبة الشاملة - الذكاء الاصطناعي (Shamela AI)",
            author = "المكتبة الشاملة",
            category = "AI & الفتوى",
            url = "https://shamela.ws/ai",
            notes = "البحث الدلالي والذكي في مئات آلاف الكتب والمصادر الإسلامية",
            isFavorite = true
        ),
        BookEntity(
            title = "دار الإفتاء ديوبند (Darul Ifta Online)",
            author = "دار العلوم ديوبند",
            category = "AI & الفتوى",
            url = "https://m.darulifta.info/#/",
            notes = "البحث في آلاف الفتاوى الشرعية المعتمدة وطرح الأسئلة الفقهية",
            isFavorite = true
        ),
        BookEntity(
            title = "الإخلاص أونلاين AI (Al-Ikhlas Online AI)",
            author = "Al-Ikhlas Online",
            category = "AI & الفتوى",
            url = "https://alikhlasonline.ai/",
            notes = "المساعد الإسلامي الذكي للإجابة عن الأسئلة الشرعية مع توثيق الأدلة",
            isFavorite = true
        )
    )
}
