package com.example.ui

enum class AppLanguage {
    URDU,
    ARABIC
}

object Strings {
    fun appName(lang: AppLanguage): String = "المكتبة الحكمه"

    fun appSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "اسلامی ڈیجیٹل لائبریری و ریڈر"
        AppLanguage.ARABIC -> "المكتبة الإسلامية الرقمية وقارئ الكتب"
    }

    fun searchHint(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کتاب کا نام یا مصنف کا نام تلاش کریں..."
        AppLanguage.ARABIC -> "ابحث عن عنوان الكتاب أو اسم المؤلف..."
    }

    fun globalSearchBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مکمل لائبریری میں تلاش"
        AppLanguage.ARABIC -> "بحث شامل في كافة الكتب"
    }

    fun searchResultsCount(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "تلاش کے نتائج: $count کتب"
        AppLanguage.ARABIC -> "نتائج البحث: $count كتب"
    }

    fun clearSearch(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "تلاش ختم کریں"
        AppLanguage.ARABIC -> "مسح البحث"
    }

    fun allCategories(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "تمام کتب"
        AppLanguage.ARABIC -> "جميع الكتب"
    }

    fun favorites(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "پسندیدہ"
        AppLanguage.ARABIC -> "المفضلة"
    }

    fun addBook(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "نئی کتاب شامل کریں"
        AppLanguage.ARABIC -> "إضافة كتاب جديد"
    }

    fun addCategory(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "نیا سیکشن"
        AppLanguage.ARABIC -> "قسم جديد"
    }

    fun categoryLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "سیکشن / زمرہ"
        AppLanguage.ARABIC -> "القسم / التصنيف"
    }

    fun bookTitleLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کتاب کا نام"
        AppLanguage.ARABIC -> "عنوان الكتاب"
    }

    fun bookAuthorLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مصنف (اختیاری)"
        AppLanguage.ARABIC -> "المؤلف (اختياري)"
    }

    fun bookUrlLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "ویب لنک (Web Link / URL)"
        AppLanguage.ARABIC -> "رابط الكتاب (Web Link / URL)"
    }

    fun bookNotesLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مختصر وضاحت / نوٹس"
        AppLanguage.ARABIC -> "نبذة مختصرة / ملاحظات"
    }

    fun saveAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "محفوظ کریں"
        AppLanguage.ARABIC -> "حفظ"
    }

    fun cancelAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "منسوخ"
        AppLanguage.ARABIC -> "إلغاء"
    }

    fun readNow(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مطالعہ کریں"
        AppLanguage.ARABIC -> "اقرأ الآن"
    }

    fun openBrowser(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "براؤزر میں کھولیں"
        AppLanguage.ARABIC -> "فتح في المتصفح"
    }

    fun copyLink(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "لنک کاپی کریں"
        AppLanguage.ARABIC -> "نسخ الرابط"
    }

    fun linkCopied(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "لنک کاپی کر لیا گیا ہے"
        AppLanguage.ARABIC -> "تم نسخ الرابط بنجاح"
    }

    fun deleteAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "حذف کریں"
        AppLanguage.ARABIC -> "حذف"
    }

    fun editAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "ترمیم کریں"
        AppLanguage.ARABIC -> "تعديل"
    }

    fun shareAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "شیئر کریں"
        AppLanguage.ARABIC -> "مشاركة"
    }

    fun confirmDeleteTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کیا آپ واقعی حذف کرنا چاہتے ہیں؟"
        AppLanguage.ARABIC -> "تأكيد الحذف"
    }

    fun confirmDeleteMsg(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "یہ کتاب آپ کی فہرست سے مستقل طور پر حذف ہو جائے گی۔"
        AppLanguage.ARABIC -> "سيتم حذف هذا الكتاب نهائياً من قائمتك."
    }

    fun emptyBooks(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کوئی کتاب موجود نہیں ہے"
        AppLanguage.ARABIC -> "لا توجد كتب حالياً"
    }

    fun emptySearch(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کوئی نتیجہ نہیں ملا"
        AppLanguage.ARABIC -> "لم يتم العثور على نتائج"
    }

    fun emptySubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "شاملہ سے کتاب کا لنک کاپی کر کے یہاں شامل کریں۔"
        AppLanguage.ARABIC -> "يمكنك إضافة أي كتاب جديد عبر رابط الشاملة."
    }

    fun booksCountSuffix(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کتب"
        AppLanguage.ARABIC -> "كتب"
    }

    fun enterTitleError(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "برائے مہربانی کتاب کا نام درج کریں"
        AppLanguage.ARABIC -> "يرجى إدخال عنوان الكتاب"
    }

    fun enterUrlError(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "برائے مہربانی درست لنک درج کریں"
        AppLanguage.ARABIC -> "يرجى إدخال رابط صحيح"
    }

    fun categoryNamePrompt(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "سیکشن کا نام درج کریں"
        AppLanguage.ARABIC -> "اسم القسم الجديد"
    }

    fun categoryNameSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "سیکشن کی وضاحت (اختیاری)"
        AppLanguage.ARABIC -> "وصف القسم (اختياري)"
    }

    fun errorLoading(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "صفحہ لوڈ کرنے میں دشواری پیش آئی"
        AppLanguage.ARABIC -> "تعذر تحميل الصفحة"
    }

    fun retryAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "دوبارہ کوشش کریں"
        AppLanguage.ARABIC -> "إعادة المحاولة"
    }

    fun ayahTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "آیت مبارکہ"
        AppLanguage.ARABIC -> "آية وتدبر"
    }

    fun ayahOfTheMomentHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "AYAH OF THE MOMENT"
        AppLanguage.ARABIC -> "آية اليوم المباركة"
    }

    fun quickAccess(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "فوری رسائی"
        AppLanguage.ARABIC -> "الوصول السريع"
    }

    fun quranTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "قرآن و تفاسیر"
        AppLanguage.ARABIC -> "القرآن والتفاسیر"
    }

    fun hadithTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "حدیث و سنن"
        AppLanguage.ARABIC -> "الحديث والسنن"
    }

    fun hanafiFiqhTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "فقہ حنفی"
        AppLanguage.ARABIC -> "الفقه الحنفي"
    }

    fun libraryCenter(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "المكتبة"
        AppLanguage.ARABIC -> "المكتبة"
    }

    fun readTafsir(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "تفسیر پڑھیں"
        AppLanguage.ARABIC -> "قراءة التفسير"
    }

    fun ayahCopied(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "آیت مبارکہ کاپی کر لی گئی ہے"
        AppLanguage.ARABIC -> "تم نسخ الآية الكريمة بنجاح"
    }

    fun aiDialogTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "اسلامی اے آئی اور فتاویٰ"
        AppLanguage.ARABIC -> "الذكاء الاصطناعي والفتوى"
    }

    fun aiDialogSubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "شاملہ، دار الافتاء اور معتمد اسلامی اے آئی سروسز"
        AppLanguage.ARABIC -> "خدمات الشاملة الذكية ودار الإفتاء والبحث الموثق"
    }

    fun openInAppReader(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "ایپ میں مطالعہ کریں"
        AppLanguage.ARABIC -> "اقرأ في التطبيق"
    }

    fun localizedCategoryName(name: String, lang: AppLanguage): String {
        return when (name) {
            "فقہ حنفی" -> when (lang) {
                AppLanguage.URDU -> "فقہ حنفی"
                AppLanguage.ARABIC -> "الفقه الحنفي"
            }
            "AI & الفتوى" -> when (lang) {
                AppLanguage.URDU -> "اسلامی اے آئی و فتاویٰ"
                AppLanguage.ARABIC -> "الذكاء الاصطناعي والفتوى"
            }
            "Tafsir" -> when (lang) {
                AppLanguage.URDU -> "تفسیر قرآن"
                AppLanguage.ARABIC -> "التفسير"
            }
            "Hadith" -> when (lang) {
                AppLanguage.URDU -> "حدیث شریف"
                AppLanguage.ARABIC -> "الحديث الشريف"
            }
            "Fiqh" -> when (lang) {
                AppLanguage.URDU -> "فقہ اسلامی"
                AppLanguage.ARABIC -> "الفقه العام"
            }
            "Aqidah" -> when (lang) {
                AppLanguage.URDU -> "عقائد و کلام"
                AppLanguage.ARABIC -> "العقيدة"
            }
            "Seerah & History" -> when (lang) {
                AppLanguage.URDU -> "سیرت و تاریخ"
                AppLanguage.ARABIC -> "السيرة والتاريخ"
            }
            "Arabic Language" -> when (lang) {
                AppLanguage.URDU -> "عربی زبان و ادب"
                AppLanguage.ARABIC -> "اللغة العربية"
            }
            else -> name
        }
    }

    fun libraryMainTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مکتبہ و کتب خانہ"
        AppLanguage.ARABIC -> "المكتبة الرئيسية"
    }

    fun librarySubtitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "علوم و اصناف کے مطابق منظم اسلامی کتب"
        AppLanguage.ARABIC -> "تصفح حسب التصنيف والعلوم الشرعية"
    }

    fun allGenres(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "تمام اصناف"
        AppLanguage.ARABIC -> "كافة التصنيفات"
    }

    fun genreBooksLabel(count: Int, lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "$count کتب"
        AppLanguage.ARABIC -> "$count كتب"
    }

    fun viewAllBooksInGenre(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "سب دیکھیں"
        AppLanguage.ARABIC -> "عرض الكل"
    }

    fun totalLibraryBooks(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کل کتب"
        AppLanguage.ARABIC -> "إجمالي الكتب"
    }

    fun totalGenres(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "اصناف / شعبہ جات"
        AppLanguage.ARABIC -> "التصنيفات"
    }

    fun viewModeShelves(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "شیلف منظر"
        AppLanguage.ARABIC -> "عرض الرفوف"
    }

    fun viewModeGrid(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "گرڈ منظر"
        AppLanguage.ARABIC -> "عرض الشبكة"
    }

    fun roomDatabaseBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مقامی ڈیٹا بیس (Room)"
        AppLanguage.ARABIC -> "قاعدة بيانات محلية (Room)"
    }

    fun noBooksInGenre(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "اس صنف میں ابھی کوئی کتاب موجود نہیں"
        AppLanguage.ARABIC -> "لا توجد كتب في هذا القسم حالياً"
    }

    fun homeTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "صفحہ اول"
        AppLanguage.ARABIC -> "الرئيسية"
    }

    fun libraryTab(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "المكتبة"
        AppLanguage.ARABIC -> "المكتبة"
    }
}

