var GcSpread;
(function (GcSpread) {
    (function (Sheets) {
        (function (designer) {
            (function (jp_res) {
                jp_res.title = "SpreadJS デザイナ";
                jp_res.defaultFont = "10pt Arial";
                jp_res.ok = "OK";
                jp_res.yes = "はい";
                jp_res.no = "いいえ";
                jp_res.apply = "適用";
                jp_res.cancel = "キャンセル";
                jp_res.close = "閉じる";

                jp_res.saveFileDialogTitle = "名前を付けて保存";
                jp_res.openFileDialogTitle = "開く";
                jp_res.allSpreadFileFilter = 'すべての Spreadsheet ファイル (*.ssjson *.xlsx *.xls *.csv)';
                jp_res.spreadFileFilter = 'SpreadJSファイル (*.ssjson)';
                jp_res.ssJSONToJSFilter = 'Javascriptファイル (*.js)';
                jp_res.allExcelFilter = "すべての Excel ファイル (*.xlsx *.xls)";
                jp_res.excelFileFilter = 'Excel 2007 ブック (*.xlsx)';
                jp_res.excelFileFilter2 = 'Excel 97-2003 ブック (*.xls)';
                jp_res.csvFileFilter = "CSV ファイル (*.csv)";
                jp_res.pdfFileFilter = "PDF ファイル (*.pdf)";
                jp_res.allFileFilter = 'すべてのファイル (*.*)';
                jp_res.importFileDialogTitle = "インポート";
                jp_res.exportFileDialogTitle = "エクスポート";

                jp_res.insertCellInSheet = "シート全体をシフトさせることはできません。";
                jp_res.insertCellInMixtureRange = "重複する選択範囲に対してそのコマンドを使用することはできません。";
                jp_res.NotExecInMultiRanges = "複数範囲に対してそのコマンドを使用することはできません。一つの範囲を選択し、もう一度実行してください。";
                jp_res.unsavedWarning = "変更内容を保存しますか？";

                jp_res.requestTempalteFail = "テンプレートファイルエラー";
                jp_res.requestTemplateConfigFail = "テンプレート設定ファイルエラー";
                jp_res.openFileFormatError = "このファイル形式には対応していません。正しい形式のファイルを選択してください。";

                jp_res.closingNotification = "現在のファイルは変更されています。\n変更内容を保存しますか？";

                jp_res.noLicense = "ライセンス情報がありません。LOCKボタンをクリックしてライセンス認証を行ってください。";
                jp_res.trialLicense = "トライアル版はあと {0} 日で終了します。LOCKボタンをクリックしてライセンス認証を行ってください。";
                jp_res.expiredLicense = "ライセンス期限になりました。LOCKボタンをクリックしてライセンス認証を行ってください。";

                jp_res.sameSlicerName = "Slicer name already in use. Please enter a unique name.";
                jp_res.nullSlicerName = "Slicer name is not valid.";

                jp_res.fontPicker = {
                    familyLabelText: 'フォント:',
                    styleLabelText: 'スタイル:',
                    sizeLabelText: 'サイズ:',
                    weightLabelText: 'フォントの太さ:',
                    colorLabelText: '色:',
                    normalFontLabelText: '標準フォント',
                    previewLabelText: 'プレビュー',
                    previewText: 'Aaあぁアァ亜宇',
                    effects: "効果",
                    underline: "下線",
                    strikethrough: "取り消し線",
                    //
                    // Fonts shown in font selector.
                    //
                    // the property's name means the font family name.
                    // the property's value means the text shown in drop down list.
                    //
                    fontFamilies: {
                        "Arial": "Arial",
                        "'Arial Black'": "Arial Black",
                        "Calibri": "Calibri",
                        "Cambria": "Cambria",
                        "Candara": "Candara",
                        "Century": "Century",
                        "'Courier New'": "Courier New",
                        "'Comic Sans MS'": "Comic Sans MS",
                        "Garamond": "Garamond",
                        "Georgia": "Georgia",
                        "'Malgun Gothic'": "Malgun Gothic",
                        "Mangal": "Mangal",
                        "Tahoma": "Tahoma",
                        "Times": "Times",
                        "'Times New Roman'": "Times New Roman",
                        "'Trebuchet MS'": "Trebuchet MS",
                        "Verdana": "Verdana",
                        "Wingdings": "Wingdings",
                        "Meiryo, メイリオ": "メイリオ",
                        "'MS Gothic', 'ＭＳ ゴシック'": "ＭＳ ゴシック",
                        "'MS Mincho', 'ＭＳ 明朝'": "ＭＳ 明朝",
                        "'MS PGothic', 'ＭＳ Ｐゴシック'": "ＭＳ Ｐゴシック",
                        "'MS PMincho', 'ＭＳ Ｐ明朝'": "ＭＳ Ｐ明朝"
                    },
                    fontStyles: {
                        'normal': '標準',
                        'italic': '斜体',
                        'oblique': 'Oblique'
                    },
                    fontWeights: {
                        'normal': '標準',
                        'bold': '太字',
                        'bolder': 'Bolder',
                        'lighter': 'Lighter'
                    },
                    alternativeFonts: "Arial,'Segoe UI',Thonburi,Verdana,Sans-Serif",
                    defaultSize: '10'
                };

                jp_res.commonFormats = {
                    Number: {
                        format: "0.00",
                        label: "数値"
                    },
                    Currency: {
                        format: "$#,##0.00",
                        label: "通貨"
                    },
                    Accounting: {
                        format: "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
                        label: "会計"
                    },
                    ShortDate: {
                        format: "m/d/yyyy",
                        label: "短い日付形式"
                    },
                    LongDate: {
                        format: "dddd, mmmm dd, yyyy",
                        label: "長い日付形式"
                    },
                    Time: {
                        format: "h:mm:ss AM/PM",
                        label: "時刻"
                    },
                    Percentage: {
                        format: "0%",
                        label: "パーセンテージ"
                    },
                    Fraction: {
                        format: "# ?/?",
                        label: "分数"
                    },
                    Scientific: {
                        format: "0.00E+00",
                        label: "指数"
                    },
                    Text: {
                        format: "@",
                        label: "テキスト"
                    },
                    Comma: {
                        format: " #,##0.00; (#,##0.00); \"-\"??;@",
                        label: "桁区切り"
                    }
                };
                jp_res.customFormat = "ユーザー設定";
                jp_res.generalFormat = "標準";

                jp_res.colorPicker = {
                    themeColorsTitle: "テーマの色",
                    standardColorsTitle: "標準の色",
                    noFillText: "塗りつぶしなし",
                    moreColorsText: "その他の色...",
                    colorDialogTitle: "色",
                    red: "赤: ",
                    green: "緑: ",
                    blue: "青: ",
                    newLabel: "新規",
                    currentLabel: "現在の色"
                };

                jp_res.formatDialog = {
                    title: "セルの書式設定",
                    number: '表示形式',
                    alignment: '配置',
                    font: 'フォント',
                    border: '罫線',
                    fill: '塗りつぶし',
                    protection: '保護',
                    category: 'カテゴリ:',
                    backColor: '背景色',
                    textAlignment: '文字の配置',
                    horizontalAlignment: '水平方向:',
                    verticalAlignment: '垂直方向:',
                    indent: 'インデント:',
                    textControl: '文字の制御',
                    wrapText: '折り返して全体を表示する',
                    shrink: '縮小して全体を表示する',
                    merge: 'セルの結合',
                    top: '上詰め',
                    bottom: '下詰め',
                    left: '左詰め',
                    right: '右詰め',
                    center: '中央揃え',
                    general: '標準',
                    sampleText: '文字列',
                    cantMergeMessage: '重なる範囲を結合することができません。',
                    lock: "ロック",
                    lockComments: "ワークシートを保護しなければ、セルをロックした効果は得られません (ワークシートを保護するには、[校閲] タブの [変更] グループにある [シートの保護] をクリックしてください)。",
                    backGroundColor: "背景色:",
                    moreColorsText: "その他の色",
                    sample: "サンプル"
                };

                jp_res.formatComment = {
                    title: "コメントの書式設定",
                    protection: "保護",
                    commentLocked: "ロック",
                    commentLockText: "文字列のロック",
                    commentLockComments: "シートを保護しなければ、オブジェクトをロックした効果は得られません。シートを保護するには、[ホーム] タブの [書式] をクリックし、[シートの保護] をクリックしてください。必要に応じて、パスワードを設定できます。",
                    properties: "プロパティ",
                    positioning: "オブジェクトの位置関係",
                    internalMargin: "内部の余白",
                    moveSize: "セルに合わせて移動やサイズ変更をする",
                    moveNoSize: "セルに合わせて移動するがサイズは変更しない",
                    noMoveSize: "セルに合わせて移動やサイズ変更をしない",
                    automatic: "自動",
                    autoSize: "自動サイズ調整",
                    colors: "色と線",
                    size: "サイズ",
                    fill: "塗りつぶし",
                    line: "線",
                    height: "高さ",
                    width: "幅",
                    lockRatio: "縦横比を固定する",
                    color: "色",
                    transparency: "透明",
                    style: "スタイル",
                    dotted: "点線",
                    dashed: "破線",
                    solid: "実線",
                    double: "二重線",
                    none: "なし",
                    groove: "Groove",
                    ridge: "Ridge",
                    inset: "Inset",
                    outset: "Outset",
                    px: "ピクセル"
                };

                jp_res.categories = {
                    general: "標準",
                    numbers: "数値",
                    currency: "通貨",
                    accounting: "会計",
                    date: "日付",
                    time: "時刻",
                    percentage: "パーセンテージ",
                    fraction: "分数",
                    scientific: "指数",
                    text: "テキスト",
                    special: "その他",
                    custom: "ユーザー定義 "
                };

                jp_res.formatNumberComments = {
                    generalComments: "セルの値に対して一般の書式を適用します（特定の書式を指定しません）",
                    numberComments: "数値の表示形式を設定します。 通貨等の特別書式については［通貨］または［会計］を選択してください。",
                    currencyComments: "通貨の表示形式を設定します。 小数点位置を揃える場合は、［会計］を選択してください。",
                    accountingComments: "［会計］は、通貨記号と小数点位置を揃えます。",
                    dateComments: "［日付］は、日付/時刻のシリアル値を日付形式で表示します。 ",
                    timeComments: "［時刻］は、日付/時刻のシリアル値を日付形式で表示します。 ",
                    percentageComments: "［パーセンテージ］は、セルの値の百分率にパーセント記号を付けて表示します。",
                    textComments: "［文字列］は、数値も文字列として扱います。 セルには入力した値がそのまま表示されます。",
                    specialComments: "［その他］は、リストやデータベースの管理に使用します。",
                    customComments: "基になる組み込みの表示形式を選択し、新しい表示形式を入力してください。"
                };

                jp_res.formatNumberPickerSetting = {
                    type: "種類:",
                    decimalPlaces: "小数点以下の桁数:",
                    symbol: "記号:",
                    negativeNumber: "負の数の表示形式:",
                    separator: "桁区切り（，）を使用する",
                    deleted: "削除",
                    locale: "ロケール (国または地域):",
                    calendar: "カレンダーの種類:"
                };

                jp_res.localeType = {
                    en_us: "英語(米国)",
                    ja_jp: "日本語"
                };

                jp_res.calendarType = {
                    western: "西暦",
                    JER: "和暦"
                };

                jp_res.fractionFormats = [
                    "# ?/?",
                    "# ??/??",
                    "# ???/???",
                    "# ?/2",
                    "# ?/4",
                    "# ?/8",
                    "# ??/16",
                    "# ?/10",
                    "# ??/100"
                ];

                jp_res.numberFormats = [
                    "0",
                    "0;[Red]0",
                    "0_);(0)",
                    "0_);[Red](0)",
                    "#,##0",
                    "#,##0;[Red]#,##0",
                    "#,##0_);(#,##0)",
                    "#,##0_);[Red](#,##0)"
                ];

                jp_res.dateFormats = [
                    "m/d/yyyy",
                    "[$-F800]dddd, mmmm dd, yyyy",
                    "m/d;@",
                    "m/d/yy;@",
                    "mm/dd/yy;@",
                    "[$-409]d-mmm;@",
                    "[$-409]d-mmm-yy;@",
                    "[$-409]dd-mmm-yy;@",
                    "[$-409]mmm-yy;@",
                    "[$-409]mmmm-yy;@",
                    "[$-409]mmmm d, yyyy;@",
                    "[$-409]m/d/yy h:mm AM/PM;@",
                    "m/d/yy h:mm;@",
                    "[$-409]mmmmm;@",
                    "[$-409]mmmmm-yy;@",
                    "m/d/yyyy;@",
                    "[$-409]d-mmm-yyyy;@"
                ];

                jp_res.japanWesternDateFormat = [
                    "yyyy'年'm'月'd'日';@",
                    "yyyy'年'm'月';@",
                    "m'月'd'日';@",
                    "yyyy/m/d;@",
                    "[$-409]yyyy/m/d h:mm AM/PM;@",
                    "yyyy/m/d h:mm;@",
                    "m/d;@",
                    "m/d/yy;@",
                    "mm/dd/yy;@",
                    "[$-409]d-mmm;@",
                    "[$-409]d-mmm-yy;@",
                    "[$-409]dd-mmm-yy;@",
                    "[$-409]mmm-yy;@",
                    "[$-409]mmmm-yy;@",
                    "[$-409]mmmmm;@",
                    "[$-409]mmmmm-yy;@"
                ];

                jp_res.japanEmperorReignDateFormat = [
                    "[$-411]ge.m.d;@",
                    "[$-411]ggge'年'm'月'd'日';@"
                ];

                jp_res.timeFormats = [
                    "[$-F400]h:mm:ss AM/PM",
                    "h:mm;@",
                    "[$-409]h:mm AM/PM;@",
                    "h:mm:ss;@",
                    "[$-409]h:mm:ss AM/PM;@",
                    "mm:ss.0;@",
                    "[h]:mm:ss;@",
                    "[$-409]m/d/yy h:mm AM/PM;@",
                    "m/d/yy h:mm;@"
                ];

                jp_res.japanTimeFormats = [
                    "h:mm;@",
                    "[$-409]h:mm AM/PM;@",
                    "h:mm:ss;@",
                    "[$-409]h:mm:ss AM/PM;@",
                    "[$-409]yyyy/m/d h:mm AM/PM;@",
                    "yyyy/m/d h:mm;@",
                    "h'時'mm'分';@",
                    "h'時'mm'分'ss'秒';@"
                ];

                jp_res.textFormats = [
                    "@"
                ];

                jp_res.specialFormats = [
                    "00000",
                    "00000-0000",
                    "[<=9999999]###-####;(###) ###-####",
                    "000-00-0000"
                ];

                jp_res.specialJapanFormats = [
                    "[<=999]000;[<=9999]000-00;000-0000",
                    "[<=99999999]####-####;(00) ####-####",
                    "'△' #,##0;'▲' #,##0",
                    "[DBNum1][$-411]General",
                    "[DBNum2][$-411]General",
                    "[DBNum3][$-411]0",
                    "[DBNum3][$-411]#,##0"
                ];

                jp_res.currencyFormats = [
                    "#,##0",
                    "#,##0;[Red]#,##0",
                    "#,##0;-#,##0",
                    "#,##0;[Red]-#,##0"
                ];

                jp_res.percentageFormats = [
                    "0%"
                ];

                jp_res.scientificFormats = [
                    "0E+00"
                ];

                jp_res.accountingFormats = [
                    "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)"
                ];

                jp_res.customFormats = [
                    "General",
                    "0",
                    "0.00",
                    "#,##0",
                    "#,##0.00",
                    "#,##0;(#,##0)",
                    "#,##0;[Red](#,##0)",
                    "#,##0.00;(#,##0.00)",
                    "#,##0.00;[Red](#,##0.00)",
                    "$#,##0;($#,##0)",
                    "$#,##0;[Red]($#,##0)",
                    "$#,##0.00;($#,##0.00)",
                    "$#,##0.00;[Red]($#,##0.00)",
                    "0%",
                    "0.00%",
                    "0.00E+00",
                    "##0.0E+0",
                    "# ?/?",
                    "# ??/??",
                    "m/d/yyyy",
                    "d-mmm-yy",
                    "d-mmm",
                    "mmm-yy",
                    "h:mm AM/PM",
                    "h:mm:ss AM/PM",
                    "hh:mm",
                    "hh:mm:ss",
                    "m/d/yyyy hh:mm",
                    "mm:ss",
                    "mm:ss.0",
                    "@",
                    "[h]:mm:ss",
                    "$ #,##0;$ (#,##0);$ \"-\";@",
                    " #,##0; (#,##0); \"-\";@",
                    "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
                    " #,##0.00; (#,##0.00); \"-\"??;@",
                    "hh:mm:ss",
                    "00000",
                    "# ???/???",
                    "000-00-0000",
                    "[$-4]dddd, mmmm dd, yyyy",
                    "m/d;@",
                    "[<=9999999]###-####;(###) ###-####",
                    "# ?/8"
                ];

                jp_res.accountingSymbol = [
                    ["なし", null, null],
                    ["$", "", "en-US"]
                ];

                jp_res.specialType = [
                    "郵便番号",
                    "Zip Code + 4",
                    "電話番号",
                    "Social Security Number"
                ];

                jp_res.specialJapanType = [
                    "郵便番号",
                    "電話番号（東京)",
                    "正負記号 （+ = △; - = ▲)",
                    "漢数字（十二万三千四百）",
                    "大字 (壱拾弐萬参阡四百)",
                    "全角 (１２３４５)",
                    "全角 桁区切り（１２,３４５）"
                ];

                jp_res.fractionType = [
                    "1 桁増加 (1/4)",
                    "2 桁増加 (21/25)",
                    "3 桁増加 (312/943)",
                    "分母を 2 に設定 (1/2)",
                    "分母を 4 に設定 (2/4)",
                    "分母を 8 に設定 (4/8)",
                    "分母を 16 に設定 (8/16)",
                    "分母を 10 に設定 (3/10)",
                    "分母を 100 に設定 (30/100)"
                ];

                jp_res.negativeNumbers = {
                    "-1234.10": "-1234.10",
                    "red:1234.10": "1234.10",
                    "(1234.10)": "(1234.10)",
                    "red:(1234.10)": "(1234.10)"
                };

                jp_res.currencyNegativeNumbers = {
                    "number1": "-1,234.10",
                    "red:number2": "1,234.10",
                    "number3": "-1,234.10",
                    "red:number4": "-1,234.10"
                };

                jp_res.rowHeightDialog = {
                    title: "行の高さ",
                    rowHeight: "行の高さ:",
                    exception: "行の高さは整数または少数点数で設定する必要があります。",
                    exception2: "0から9999999の値を設定してください。"
                };
                jp_res.columnWidthDialog = {
                    title: "列の幅",
                    columnWidth: "列の幅:",
                    exception: "列の幅は整数または少数点数で設定する必要があります。",
                    exception2: "0から9999999の値を設定してください。"
                };
                jp_res.standardWidthDialog = {
                    title: "標準の幅",
                    columnWidth: "標準の列幅:",
                    exception: "入力した数値は使用できません。 整数型または小数点数を入力してください。"
                };
                jp_res.standardHeightDialog = {
                    title: "標準の高さ",
                    rowHeight: "標準の行高:",
                    exception: "入力した数値は使用できません。 整数型または小数点数を入力してください。"
                };
                jp_res.insertCellsDialog = {
                    title: "挿入",
                    shiftcellsright: "右方向にシフト",
                    shiftcellsdown: "下方向にシフト",
                    entirerow: "行全体",
                    entirecolumn: "列全体"
                };
                jp_res.deleteCellsDialog = {
                    title: "削除",
                    shiftcellsleft: "左方向にシフト",
                    shiftcellsup: "上方向にシフト",
                    entirerow: "行全体",
                    entirecolumn: "列全体"
                };
                jp_res.groupDialog = {
                    title: "グループ",
                    rows: "行",
                    columns: "列"
                };
                jp_res.ungroupDialog = {
                    title: "グループ解除"
                };
                jp_res.findDialog = {
                    title: "検索",
                    findwhat: "検索する文字列:",
                    within: "検索場所:",
                    matchcase: "大小文字の区別",
                    search: "検索:",
                    matchexactly: "完全一致",
                    lookin: "検索対象:",
                    usewildcards: "ワイルドカードを使う",
                    option: "オプション",
                    findall: "すべて検索",
                    findnext: "次を検索",
                    exception: "検索条件に一致するデータが見つかりません。"
                };
                jp_res.gotoDialog = {
                    title: "ジャンプ",
                    goto: "移動先:",
                    reference: "参照:",
                    exception: "参照が正しくありません。",
                    wrongName: "失敗しました。"
                };
                jp_res.nameManagerDialog = {
                    title: "名前の管理",
                    newName: "新規作成...",
                    edit: "編集...",
                    deleteName: "削除",
                    filterWith: {
                        title: "フィルター:",
                        clearFilter: "フィルターのクリア",
                        nameScopedToWorkbook: "ブックを範囲とした名前",
                        nameScopedToWorksheet: "ワークシートを範囲とした名前",
                        nameWithErrors: "エラーのある名前",
                        nameWithoutErrors: "エラーのない名前"
                    },
                    nameColumn: "名前",
                    valueColumn: "値",
                    refersToColumn: "参照範囲",
                    scopeColumn: "範囲",
                    exception1: "入力した名前は無効です。",
                    exception2: "入力した名前は既に存在します。一意の名前を入力してください。",
                    deleteAlert: "名前 {0} を削除してもよろしいですか?"
                };
                jp_res.newNameDialog = {
                    titleNew: "新しい名前",
                    titleEdit: "名前の編集",
                    name: "名前:",
                    scope: {
                        title: "範囲:",
                        workbook: "ブック"
                    },
                    referTo: "参照範囲:",
                    wrongName: "入力した名前は正しくありません。"
                };
                jp_res.insertFunctionDialog = {
                    title: "関数の挿入",
                    functionCategory: "関数の分類:",
                    functionList: "関数名:",
                    formula: "数式:",
                    functionCategorys: "すべて表示,データベース,日付/時刻,エンジニアリング,財務,情報,論理,検索と参照,数学/三角,統計,文字列操作"
                };
                jp_res.buttonCellTypeDialog = {
                    title: "コマンドボタン型セル",
                    marginGroup: "余白:",
                    left: "左:",
                    top: "上:",
                    right: "右:",
                    bottom: "下:",
                    text: "テキスト:",
                    backcolor: "背景色:",
                    other: "その他:"
                };
                jp_res.checkBoxCellTypeDialog = {
                    title: "チェックボックス型セル",
                    textGroup: "テキスト:",
                    "true": "チェック:",
                    indeterminate: "不確定:",
                    "false": "未チェック:",
                    align: "テキストの位置:",
                    other: "その他:",
                    caption: "表示:",
                    isThreeState: "３ステート",
                    checkBoxTextAlign: {
                        top: "上",
                        bottom: "下",
                        left: "左",
                        right: "右"
                    }
                };
                jp_res.comboBoxCellTypeDialog = {
                    title: "コンボボックス型セル",
                    editorValueTypes: "エディタの値:",
                    items: "項目:",
                    itemProperties: "アイテムのプロパティ:",
                    text: "テキスト:",
                    value: "値:",
                    add: "追加",
                    remove: "削除",
                    editorValueType: {
                        text: "テキスト",
                        index: "インデックス",
                        value: "値"
                    },
                    editable: "編集の許可",
                    itemHeight: "アイテムの高さ"
                };
                jp_res.hyperLinkCellTypeDialog = {
                    title: "HyperLink セル型",
                    link: "リンクの色:",
                    visitedlink: "表示済の色:",
                    text: "テキスト:",
                    linktooltip: "ツールチップのテキスト:",
                    color: "色:",
                    other: "その他:"
                };
                jp_res.headerCellsDialog = {
                    title: "ヘッダエディタ",
                    rowHeader: "行ヘッダ",
                    columnHeader: "列ヘッダ",
                    backColor: "背景色",
                    borderBottom: "下罫線",
                    borderLeft: "左罫線",
                    borderRight: "右罫線",
                    borderTop: "上罫線",
                    font: "フォント",
                    foreColor: "セルのテキストの色（前景色）",
                    formatter: "フォーマッタ",
                    hAlign: "横位置",
                    height: "縦",
                    locked: "ロック",
                    resizable: "リサイズを許可する",
                    shrinkToFit: "縮小して全体を表示する",
                    value: "値",
                    textIndent: "テキストのインデント",
                    vAlign: "縦位置",
                    visible: "表示を有効にする",
                    width: "横",
                    wordWrap: "折り返して全体を表示する",
                    popUp: "...",
                    merge: "結合",
                    unmerge: "結合を解除",
                    insertRows: "挿入行",
                    addRows: "追加行",
                    deleteRows: "削除行",
                    insertColumns: "挿入列",
                    addColumns: "追加列",
                    deleteColumns: "削除列",
                    clear: "クリア",
                    top: '上詰め',
                    bottom: '下詰め',
                    left: '左詰め',
                    right: '右詰め',
                    center: '中央揃え',
                    general: '標準',
                    exception: "無効な設定です。赤く表示された部分を確認してください。"
                };
                jp_res.fontPickerDialog = {
                    title: "フォント"
                };
                jp_res.fillDialog = {
                    title: "シリーズ"
                };

                jp_res.zoomDialog = {
                    title: "ズーム",
                    double: "200%",
                    normal: "100%",
                    threeFourths: "75%",
                    half: "50%",
                    quarter: "25%",
                    fitSelection: "選択範囲をズーム",
                    custom: "ユーザー設定:",
                    exception: "入力した数値は使用できません。 整数型または小数点数を入力してください。",
                    magnification: "倍率",
                    percent: "%"
                };
                jp_res.contextMenu = {
                    cut: "切り取り ",
                    copy: "コピー",
                    paste: "Paste Options:",
                    insertDialog: "挿入...",
                    deleteDialog: "削除...",
                    clearcontents: "数式と値のクリア",
                    filter: "フィルタリング ",
                    sort: "ソート",
                    sortAToZ: "昇順",
                    sortZToA: "降順",
                    customSort: "カスタムソート...",
                    formatCells: "セルの書式設定...",
                    defineName: "名前の定義...",
                    rowHeight: "行の高さ...",
                    columnWidth: "列の幅...",
                    hide: "非表示",
                    unhide: "再表示",
                    headers: "ヘッダ...",
                    insert: "挿入",
                    "delete": "削除",
                    protectsheet: "シートの保護...",
                    unprotectsheet: "シート保護の解除...",
                    comments: "ブックのシートをすべて非表示にすることはできません。",
                    insertComment: "コメントの挿入",
                    editComment: "コメントの編集",
                    deleteComment: "コメントの削除",
                    hideComment: "コメントの非表示",
                    editText: "コメントの編集",
                    exitEditText: "テキスト編集の終了",
                    formatComment: "コメントの書式設定",
                    unHideComment: "コメントの表示/非表示",
                    sheetTabColor: "Tab Color",
                    remove: "Remove",
                    slicerProperty: "Size and Properties...",
                    slicerSetting: "Slicer Settings..."
                };
                jp_res.borderPicker = {
                    lineStyleTitle: "線:",
                    borderColorTitle: "色:",
                    none: "なし"
                };
                jp_res.borderDialog = {
                    border: "罫線",
                    presets: "プリセット",
                    none: "なし",
                    outline: "外枠",
                    inside: "内側",
                    line: "線",
                    text: "テキスト",
                    comments: "プリセット、プレビュー枠内または上のボタンをクリックすると、選択した罫線の種類が適用されます。"
                };

                jp_res.conditionalFormat = {
                    highlightCellsRules: "セルの強調表示ルール ",
                    topBottomRules: "上位/下位ルール",
                    dataBars: "データバー",
                    colorScales: "カラー スケール",
                    iconSets: "アイコンセット",
                    newRule: "新規ルール...",
                    clearRules: "ルールのクリア...",
                    manageRules: "ルールの管理...",
                    greaterThan: "指定の値より大きい...",
                    lessThan: "指定の値より小さい...",
                    between: "指定の範囲内...",
                    equalTo: "指定の値に等しい...",
                    textThatContains: "文字列...",
                    aDateOccurring: "日付...",
                    duplicateValues: "重複する値...",
                    moreRules: "その他のルール...",
                    top10Items: "上位 10 項目...",
                    bottom10Items: "下位 10 項目...",
                    aboveAverage: "平均より上...",
                    belowAverage: "平均より下...",
                    gradientFill: "塗りつぶし (グラデーション)",
                    solidFill: "塗りつぶし (単色)",
                    directional: "方向",
                    shapes: "図形",
                    indicators: "インジケーター",
                    ratings: "評価",
                    clearRulesFromSelectedCells: "選択したセルからルールをクリア",
                    clearRulesFromEntireSheet: "シート全体からルールをクリア"
                };

                jp_res.fileMenu = {
                    _new: "新規作成",
                    open: "開く",
                    save: "保存",
                    saveAs: "名前を付けて保存",
                    _export: "エクスポート",
                    _import: "インポート",
                    exit: "終了",
                    recentWorkbooks: "最近使用したブック",
                    computer: "コンピュータ",
                    currentFolder: "現在のフォルダ",
                    recentFolders: "最近参照したフォルダ",
                    browse: "参照",
                    spreadSheetJsonFile: "SpreadSheet ファイル (JSON)",
                    excelFile: "Excel ファイル",
                    csvFile: "CSV ファイル",
                    pdfFile: "PDF ファイル",
                    importSpreadSheetFile: "JSONファイル",
                    importExcelFile: "エクセルファイル",
                    importCsvFile: "CSV ファイル",
                    exportSpreadSheetFile: "JSONファイル",
                    exportExcelFile: "エクセルファイル",
                    exportCsvFile: "CSV ファイル",
                    exportPdfFile: "PDF ファイル",
                    exportJSFile: "Javascript ファイル",
                    openFlags: "オープンフラグの設定",
                    importDataOnly: "データのみ",
                    importDataAndFormulasOnly: "データと数式のみ",
                    importDoNotRecalculateAfterLoad: "インポート後、数式を再計算しない",
                    importRowAndColumnHeaders: "固定列と固定行をヘッダーとしてインポートする",
                    importRowHeaders: "固定行をヘッダーとしてインポートする",
                    importColumnHeaders: "固定列をヘッダーとしてインポートする",
                    importPassword: "パスワード",
                    importIncludeRowHeader: "見出し行をインポートする",
                    importIncludeColumnHeader: "見出し列をインポートする",
                    importUnformatted: "データを書式設定されていない状態にする",
                    importImportFormula: "セルの数式をインポートする",
                    importRowDelimiter: "行区切り文字",
                    importColumnDelimiter: "列区切り文字",
                    importCellDelimiter: "セル区切り文字",
                    importEncoding: "ファイルのエンコード",
                    saveFlags: "セーブフラグの設定",
                    exportDataOnly: "データのみ",
                    exportNoFormulas: "数式をエクスポートしない",
                    exportAutoRowHeight: "行の高さの自動調整",
                    exportSaveAsFiltered: "フィルタリング結果をエクスポート",
                    exportSaveAsViewed: "表示されている状態でエクスポート",
                    exportSaveBothCustomRowAndColumnHeaders: "行ヘッダーと列ヘッダーをエクセルの見出し行、見出し列としてエクスポート",
                    exportSaveCustomRowHeaders: "行ヘッダーをエクセルの見出し行としてエクスポート",
                    exportSaveCustomColumnHeaders: "列ヘッダをエクセルの見出し行としてエクスポート",
                    exportPassword: "パスワード",
                    exportExcel97_2003File: "エクセル 97-2003 ファイル",
                    exportIncludeRowHeader: "行ヘッダを含める",
                    exportIncludeColumnHeader: "列ヘッダを含める",
                    exportUnFormatted: "スタイル情報を含めない",
                    exportFormula: "数式を含める",
                    exportAsViewed: "表示されている状態でエクスポート",
                    exportSheetIndex: "シートインデックス",
                    exportEncoding: "エンコード",
                    exportRow: "行",
                    exportColumn: "列",
                    exportRowCount: "行数",
                    exportColumnCount: "列数",
                    exportRowDelimiter: "行区切り文字",
                    exportColumnDelimiter: "列区切り文字",
                    exportCellDelimiter: "セル区切り文字",
                    exportVisibleRowCol: "可視の行および列だけを含める",
                    pdfBasicSetting: "基本設定",
                    pdfTitle: "タイトル:",
                    pdfAuthor: "作成者:",
                    pdfApplication: "アプリケーション:",
                    pdfSubject: "サブタイトル:",
                    pdfKeyWords: "キーワード:",
                    pdfExportSetting: "エクスポート設定",
                    exportSheetLabel: "エクスポートするシート:",
                    allSheet: "すべて",
                    pdfDisplaySetting: "画面設定",
                    centerWindowLabel: "ウィンドウを画面中央に表示",
                    showTitleLabel: "タイトルを表示",
                    showToolBarLabel: "ツールバーを表示",
                    fitWindowLabel: "ウィンドウを既存のサイズで表示",
                    showMenuBarLabel: "メニューバーを表示",
                    showWindowUILabel: "パネルウィンドウを表示",
                    destinationTypeLabel: "文書の表示方法:",
                    destinationType: {
                        autoDestination: "自動",
                        fitPageDestination: "ページに合わせて表示",
                        fitWidthDestination: "ウィンドウの幅に合わせて表示",
                        fitHeightDestination: "ウィンドウの高さに合わせて表示",
                        fitBoxDestination: "余白に合わせて表示"
                    },
                    openTypeLabel: "オープン時の表示設定:",
                    openType: {
                        autoOpen: "自動",
                        useNoneOpen: "文書のみ",
                        useOutlinesOpen: "文書としおり",
                        useThumbsOpen: "文書とページサムネイル",
                        fullScreenOpen: "フルスクリーンモード",
                        useOCOpen: "文書とレイヤー",
                        useAttachmentsOpen: "文書と添付ファイル"
                    },
                    pdfPageSetting: "ページ設定",
                    openPageNumberLabel: "オープン時のページ番号:",
                    pageLayoutLabel: "ページレイアウト:",
                    pageLayout: {
                        autoLayout: "自動",
                        singlePageLayout: "単一ページ表示",
                        oneColumnLayout: "ページを1列に表示",
                        twoColumnLeftLayout: "ページを2列に表示(奇数ページ左側)",
                        twoColumnRightLayout: "ページを2列に表示(奇数ページ右側)",
                        twoPageLeftLayout: "見開き表示(奇数ページ左側)",
                        twoPageRight: "見開き表示(奇数ページ右側)"
                    },
                    pageDurationLabel: "ページ表示時間:",
                    pageTransitionLabel: "ページ切替効果:",
                    pageTransition: {
                        defaultTransition: "既定",
                        splitTransition: "Split",
                        blindsTransition: "Blinds",
                        boxTransition: "Box",
                        wipeTransition: "Wipe",
                        dissolveTransition: "Dissolve",
                        glitterTransition: "Glitter",
                        flyTransition: "Fly",
                        pushTransition: "Push",
                        coverTransition: "Cover",
                        uncoverTransition: "Uncover",
                        fadeTransition: "Fade"
                    },
                    printerSetting: "印刷設定...",
                    printerSettingDialogTitle: "印刷設定",
                    copiesLabel: "部数:",
                    scalingTypeLabel: "拡縮設定:",
                    scalingType: {
                        appDefaultScaling: "既定",
                        noneScaling: "なし"
                    },
                    duplexModeLabel: "両面印刷設定:",
                    duplexMode: {
                        defaultDuplex: "既定",
                        simplexDuplex: "片面印刷",
                        duplexFlipShortEdge: "両面印刷(短辺綴じ込み)",
                        duplexFlipLongEdge: "両面印刷(長辺綴じ込み)"
                    },
                    choosePaperSource: "PDFのページサイズに合わせて用紙を選択",
                    printRanges: "印刷範囲",
                    indexLabel: "インデックス",
                    countLabel: "範囲",
                    addRange: "追加",
                    removeRange: "削除",
                    addRangeException: "無効な値です。インデックス値は0以上、範囲は0より大きい値である必要があります。",
                    noRecentWorkbooks: "最近使用したブックはありません。まずはじめにブックを開いてください。",
                    noRecentFolders: "最近参照したフォルダはありません。まずはじめにブックを開いてください。"
                };

                jp_res.formatTableStyle = {
                    name: "名前:",
                    tableElement: "テーブル要素:",
                    preview: "プレビュー",
                    format: "書式",
                    tableStyle: "テーブルスタイル",
                    clear: "クリア",
                    stripeSize: "縞のサイズ",
                    light: "淡色",
                    medium: "中間",
                    dark: "濃色",
                    newTableStyle: "新しいテーブルスタイル...",
                    custom: "ユーザー設定",
                    exception: "スタイル名が登録されました。",
                    title: "SpreadJS デザイナ"
                };
                jp_res.tableElement = {
                    wholeTableStyle: "テーブル全体",
                    firstColumnStripStyle: "最初の列のストライプ",
                    secondColumnStripStyle: "2 番目の列のストライプ",
                    firstRowStripStyle: "最初の行のストライプ",
                    secondRowStripStyle: "2 番目の行のストライプ",
                    highlightLastColumnStyle: "最後の列",
                    highlightFirstColumnStyle: "最初の列",
                    headerRowStyle: "見出し行",
                    footerRowStyle: "集計行",
                    firstHeaderCellStyle: "最初の見出しセル",
                    lastHeaderCellStyle: "最後の見出しセル",
                    firstFooterCellStyle: "最初の集計セル",
                    lastFooterCellStyle: "最後の集計セル"
                };
                jp_res.conditionalFormatting = {
                    common: {
                        'with': "書式",
                        selectedRangeWith: "選択範囲内での書式",
                        and: "と"
                    },
                    greaterThan: {
                        title: "次の値より大きい",
                        description: "次の値より大きいセルを書式設定:"
                    },
                    lessThan: {
                        title: "次の値より小さい",
                        description: "次の値より小さいセルを書式設定:"
                    },
                    between: {
                        title: "次の値の間",
                        description: "次の範囲にあるセルを書式設定:"
                    },
                    equalTo: {
                        title: "次の値に等しい",
                        description: "次の値に等しいセルを書式設定:"
                    },
                    textThatCotains: {
                        title: "文字列",
                        description: "次の文字列を含むセルを書式設定:"
                    },
                    dateOccurringFormat: {
                        title: "日付",
                        description: "次の期間内の日付を含むセルを書式設定:",
                        date: {
                            yesterday: "昨日",
                            today: "今日",
                            tomorrow: "明日",
                            last7days: "過去 7 日間",
                            lastweek: "先週",
                            thisweek: "今週",
                            nextweek: "来週",
                            lastmonth: "先月",
                            thismonth: "今月",
                            nextmonth: "来月"
                        }
                    },
                    duplicateValuesFormat: {
                        title: "重複する値",
                        description: "次の値を含むセルを書式設定:",
                        type: {
                            duplicate: "重複",
                            unique: "一意"
                        },
                        valueswith: "値   書式:"
                    },
                    top10items: {
                        title: "上位 10 項目",
                        description: "上位に入るセルを書式設定:"
                    },
                    bottom10items: {
                        title: "下位 10 項目",
                        description: "下位に入るセルを書式設定:"
                    },
                    aboveAverage: {
                        title: "平均より上",
                        description: "平均より上のセルを書式設定:"
                    },
                    belowAverage: {
                        title: "平均より下",
                        description: "平均より下のセルを書式設定:"
                    },
                    newFormattingRule: {
                        title: "新しい書式ルール",
                        title2: "書式ルールの編集",
                        description1: "ルールの種類を選択してください:",
                        description2: "ルールの内容を編集してください:",
                        ruleType: {
                            formatOnValue: "►セルの値に基づいてすべてのセルを書式設定",
                            formatContain: "►指定の値を含むセルだけを書式設定",
                            formatRankedValue: "►上位または下位に入る値だけを書式設定",
                            formatAbove: "►平均より上または下の値だけを書式設定",
                            formatUnique: "►一意の値または重複する値だけを書式設定",
                            useFormula: "►数式を使用して、書式設定するセルを決定"
                        },
                        formatOnValue: {
                            description: "セルの値に基づいてすべてのセルを書式設定:",
                            formatStyle: "書式スタイル:",
                            formatStyleSelector: {
                                color2: "２色スケール",
                                color3: "３色スケール",
                                dataBar: "データバー",
                                iconSets: "アイコン セット"
                            },
                            color2: {
                                min: "最小値",
                                max: "最大値",
                                type: "種類:",
                                value: "値:",
                                color: "色:",
                                preview: "プレビュー",
                                minSelector: {
                                    lowest: "最小値"
                                },
                                maxSelector: {
                                    highest: "最大値"
                                }
                            },
                            color3: {
                                mid: "次の値の間"
                            },
                            dataBar: {
                                showBarOnly: "棒のみ表示",
                                auto: "自動",
                                description2: "バーの外観:",
                                fill: "塗りつぶし",
                                color: "色",
                                border: "罫線",
                                fillSelector: {
                                    solidFill: "塗りつぶし (単色)",
                                    gradientFill: "塗りつぶし (グラデーション)"
                                },
                                borderSelector: {
                                    noBorder: "格子なし",
                                    solidBorder: "罫線（実践）"
                                },
                                negativeBtn: "負の値と軸...",
                                barDirection: "棒の方向:",
                                barDirectionSelector: {
                                    l2r: "左から右",
                                    r2l: "右から左"
                                },
                                preview: "プレビュー",
                                negativeDialog: {
                                    title: "負の値と軸の設定",
                                    group1: {
                                        title: "負の棒の塗りつぶしの色",
                                        fillColor: "塗りつぶしの色:",
                                        apply: "正の棒と同じ色で塗りつぶす"
                                    },
                                    group2: {
                                        title: "負の棒の罫線の色",
                                        borderColor: "線の色:",
                                        apply: "正の棒と同じ色で塗りつぶす"
                                    },
                                    group3: {
                                        title: "軸の設定",
                                        description: "負の値の棒の表示方法を変更する、セル内の軸の位置を選択してください",
                                        radio: {
                                            auto: "(負の相対に応じてさまざまな位置に表示)",
                                            cell: "セルの中間",
                                            none: "(正の値と同じ方向に負の値の棒を表示)"
                                        },
                                        axisColor: "軸の色:"
                                    }
                                }
                            },
                            iconSets: {
                                iconStyle: "アイコン スタイル:",
                                showIconOnly: "アイコンのみ表示",
                                reverseIconOrder: "アイコンの順序を逆にする",
                                display: "次のルールに従って各アイコンを表示:",
                                icon: "アイコン",
                                value: "値",
                                type: "種類",
                                description1: "値",
                                description2: "値 < ",
                                operator: {
                                    largeOrEqu: "> =",
                                    large: ">"
                                }
                            },
                            commonSelector: {
                                num: "数値",
                                percent: "パーセント",
                                formula: "数式",
                                percentile: "百分位"
                            }
                        },
                        formatContain: {
                            description: "次のセルのみを書式設定:",
                            type: {
                                cellValue: "セルの値",
                                specificText: "特定の文字列",
                                dateOccurring: "日付",
                                blanks: "空白",
                                noBlanks: "空白なし",
                                errors: "エラー",
                                noErrors: "エラーなし"
                            },
                            operator_cellValue: {
                                between: "次の値の間",
                                notBetween: "次の値の間以外",
                                equalTo: "次の値に等しい",
                                notEqualTo: "次の値に等しくない",
                                greaterThan: "次の値より大きい",
                                lessThan: "次の値より小さい",
                                greaterThanOrEqu: "次の値以上",
                                lessThanOrEqu: "次の値以下"
                            },
                            operator_specificText: {
                                containing: "次の値を含む",
                                notContaining: "次の値を含まない",
                                beginningWith: "次の値で始まる",
                                endingWith: "次の値で終わる"
                            }
                        },
                        formatRankedValue: {
                            description: "次に入る値を書式設定:",
                            type: {
                                top: "上",
                                bottom: "下"
                            }
                        },
                        formatAbove: {
                            description: "次の値を書式設定:",
                            type: {
                                above: "次の値より上:",
                                below: "次の値より下:",
                                equalOrAbove: "次の値以上:",
                                equalOrBelow: "次の値以下:",
                                std1Above: "次の値より 1 標準偏差上:",
                                std1Below: "次の値より 1 標準偏差下:",
                                std2Above: "次の値より 2 標準偏差上:",
                                std2Below: "次の値より 2 標準偏差下:",
                                std3Above: "次の値より 3 標準偏差上:",
                                std3Below: "次の値より 3 標準偏差下:"
                            },
                            description2: "選択範囲の平均値"
                        },
                        formatUnique: {
                            description: "すべての値を書式設定:",
                            type: {
                                duplicate: "重複",
                                unique: "一意"
                            },
                            description2: "値（選択範囲内）"
                        },
                        useFormula: {
                            description: "次の数式を満たす場合に値を書式設定:"
                        },
                        preview: {
                            description: "プレビュー:",
                            buttonText: "書式...",
                            noFormat: "書式が設定されていません",
                            hasFormat: "Aaあぁアァ亜宇"
                        }
                    },
                    withStyle: {
                        lightRedFill_DarkRedText: "濃い赤の文字、明るい赤の背景",
                        yellowFill_DrakYellowText: "濃い黄色の文字、黄色の背景",
                        greenFill_DarkGreenText: "濃い緑の文字、緑の背景",
                        lightRedFill: "明るい赤の背景",
                        redText: "赤の文字",
                        redBorder: "赤の罫線",
                        customFormat: "書式..."
                    },
                    exceptions: {
                        e1: "入力された値は有効な数値、日付、時刻、文字列ではありません。",
                        e2: "値を入力してください。",
                        e3: "1から1000の値を設定してください。",
                        e4: "値を入力してください。",
                        e5: "単一のセルを参照するか、組み込み関数を使用してください。（例）=SUM(A1:E5)",
                        e6: "公式ルールのソースレンジは、単一のレンジでなければなりません。"
                    }
                };

                jp_res.formattingRulesManagerDialog = {
                    title: "条件付き書式ルールの管理",
                    rulesScopeLabel: "このワークシートの書式:",
                    rulesScopeForSelection: "現在の選択範囲",
                    rulesScopeForWorksheet: "このワークシート",
                    newRule: "新規ルール...",
                    editRule: "ルールの編集...",
                    deleteRule: "ルールの削除...",
                    gridTitleRule: "ルール (表示順で適用)",
                    gridTitleFormat: "書式",
                    gridTitleAppliesTo: "適用先",
                    gridTitleStopIfTrue: "条件を満たす場合は停止",
                    ruleDescriptions: {
                        valueBetween: 'セルの値が {0} から {1} の範囲内',
                        valueNotBetween: 'セルの値が {0} から {1} の範囲外',
                        valueEquals: 'セルの値 = {0}',
                        valueNotEquals: 'セルの値 <> {0}',
                        valueGreateThan: 'セルの値 > {0}',
                        valueGreateThanOrEquals: 'セルの値 >= {0}',
                        valueLessThan: 'セルの値 < {0}',
                        valueLessThanOrEquals: 'セルの値 <= {0}',
                        valueContains: 'セルの値が "{0}" を含む',
                        valueNotContains: 'セルの値が "{0}" を含まない',
                        valueBeginsWith: 'セルの値が "{0}" で始まる',
                        valueEndsWith: 'セルの値が "{0}" で終わる',
                        last7Days: '過去 7 日間',
                        lastMonth: '先月',
                        lastWeek: '来週',
                        nextMonth: '来月',
                        nextWeek: '来週',
                        thisMonth: '今月',
                        thisWeek: '今週',
                        today: '今日',
                        tomorrow: '明日',
                        yesterday: '昨日',
                        duplicateValues: '重複する値',
                        uniqueValues: '一意の値',
                        top: '上位 {0}',
                        bottom: '下位 {0}',
                        above: '平均より上',
                        above1StdDev: '平均より 1 標準偏差上',
                        above2StdDev: '平均より 2 標準偏差上',
                        above3StdDev: '平均より 3 標準偏差上',
                        below: '平均より下',
                        below1StdDev: '平均より 1 標準偏差下',
                        below2StdDev: '平均より 2 標準偏差下',
                        below3StdDev: '平均より 3 標準偏差下',
                        equalOrAbove: '平均以上',
                        equalOrBelow: '平均以下',
                        dataBar: 'データバー',
                        twoScale: 'グラデーション　カラースケール',
                        threeScale: 'グラデーション　カラースケール',
                        iconSet: 'アイコン　セット',
                        formula: '数式: {0}'
                    },
                    previewText: 'Aaあぁアァ亜宇'
                };

                jp_res.cellStylesDialog = {
                    cellStyles: "セルの<br>スタイル",
                    custom: "Custom",
                    goodBadAndNeutral: "良い、悪い、どちらでもない",
                    dataAndModel: "データとモデル",
                    titlesAndHeadings: "タイトルと見出し",
                    themedCellStyle: "テーマのセルスタイル",
                    numberFormat: "表示形式",
                    goodBadAndNeutralContent: {
                        "Normal": "標準",
                        "Bad": "悪い",
                        "Good": "良い",
                        "Neutral": "どちらでもない"
                    },
                    dataAndModelContent: {
                        "Calculation": "計算",
                        "Check cell": "チェック セル",
                        "Explanatory text": "説明文",
                        "Input": "入力",
                        "Linked Cell": "リンク セル",
                        "Note": "メモ",
                        "Output": "出力",
                        "Warning Text": "警告文"
                    },
                    titlesAndHeadingsContent: {
                        "Heading 1": "見出し 1",
                        "Heading 2": "見出し 2",
                        "Heading 3": "見出し 3",
                        "Heading 4": "見出し 4",
                        "Title": "タイトル",
                        "Total": "集計"
                    },
                    themedCellStyleContent: {
                        "20% - Accent1": "20% - アクセント1",
                        "20% - Accent2": "20% - アクセント2",
                        "20% - Accent3": "20% - アクセント3",
                        "20% - Accent4": "20% - アクセント4",
                        "20% - Accent5": "20% - アクセント5",
                        "20% - Accent6": "20% - アクセント6",
                        "40% - Accent1": "40% - アクセント1",
                        "40% - Accent2": "40% - アクセント2",
                        "40% - Accent3": "40% - アクセント3",
                        "40% - Accent4": "40% - アクセント4",
                        "40% - Accent5": "40% - アクセント5",
                        "40% - Accent6": "40% - アクセント6",
                        "60% - Accent1": "60% - アクセント1",
                        "60% - Accent2": "60% - アクセント2",
                        "60% - Accent3": "60% - アクセント3",
                        "60% - Accent4": "60% - アクセント4",
                        "60% - Accent5": "60% - アクセント5",
                        "60% - Accent6": "60% - アクセント6",
                        "Accent1": "アクセント1",
                        "Accent2": "アクセント2",
                        "Accent3": "アクセント3",
                        "Accent4": "アクセント4",
                        "Accent5": "アクセント5",
                        "Accent6": "アクセント6"
                    },
                    numberFormatContent: {
                        "Comma": "桁区切り",
                        "Comma [0]": "桁区切り [0]",
                        "Currency": "通貨",
                        "Currency [0]": "通貨 [0]",
                        "Percent": "パーセント"
                    },
                    newCellStyle: "新しいセルのスタイル..."
                };

                jp_res.newCellStyleDialog = {
                    style: "スタイル",
                    styleName: "スタイル名:",
                    defaultStyleName: "スタイル 1",
                    format: "書式設定...",
                    message: "このスタイル名は既に存在します。"
                };

                jp_res.cellStyleContextMenu = {
                    "delete": "削除",
                    modify: "変更"
                };

                jp_res.insertPictureDialogTitle = "Insert Picture";
                jp_res.pictureFormatFilter = {
                    jpeg: "JPEG File Interchange Format(*.jpg;*.jpeg)",
                    png: "Portable Network Graphics(*.png)",
                    bmp: "Windows Bitmap(*.bmp)",
                    allFiles: "All Files(*.*)"
                };

                jp_res.ribbon = {
                    accessBar: {
                        undo: "元に戻す",
                        redo: "やり直し",
                        save: "保存",
                        New: "新規作成",
                        open: "開く",
                        active: "アクティベーション"
                    },
                    home: {
                        file: "ファイル",
                        home: "ホーム",
                        clipboard: "クリップボード",
                        fonts: "フォント",
                        alignment: "配置",
                        numbers: "数値",
                        cellType: "セル型",
                        styles: "スタイル",
                        cells: "セル",
                        editing: "編集",
                        paste: "貼り付け",
                        all: "すべて",
                        formulas: "数式",
                        values: "値",
                        formatting: "書式",
                        cut: "切り取り",
                        copy: "コピー",
                        fontFamily: "フォント",
                        fontSize: "フォント サイズ",
                        increaseFontSize: "フォント サイズの拡大",
                        decreaseFontSize: "フォント サイズの縮小",
                        bold: "太字",
                        italic: "斜体",
                        underline: "下線",
                        border: "罫線",
                        bottomBorder: "下罫線",
                        topBorder: "上罫線",
                        leftBorder: "左罫線",
                        rightBorder: "右罫線",
                        noBorder: "格子なし",
                        allBorder: "格子",
                        outsideBorder: "外枠",
                        thickBoxBorder: "外枠太罫線",
                        bottomDoubleBorder: "下二重罫線",
                        thickBottomBorder: "下太罫線",
                        topBottomBorder: "上罫線 + 下罫線",
                        topThickBottomBorder: "上罫線 + 下太罫線",
                        topDoubleBottomBorder: "上罫線 + 下二重罫線",
                        moreBorders: "その他の罫線...",
                        backColor: "塗りつぶしの色",
                        fontColor: "フォントの色",
                        topAlign: "上詰め",
                        middleAlign: "中央揃え",
                        bottomAlign: "下詰め",
                        leftAlign: "左詰め",
                        centerAlign: "中央揃え",
                        rightAlign: "右詰め",
                        increaseIndent: "インデント",
                        decreaseIndent: "インデント解除",
                        wrapText: "折り返し",
                        mergeCenter: "結合して中央揃え",
                        mergeAcross: "横方向に結合",
                        mergeCells: "結合",
                        unMergeCells: "結合を解除",
                        numberFormat: "表示形式",
                        general: "標準",
                        Number: "数値",
                        currency: "通貨",
                        accounting: "会計",
                        shortDate: "短い日付形式",
                        longDate: "長い日付形式",
                        time: "時間",
                        percentage: "パーセンテージ",
                        fraction: "分数",
                        scientific: "指数",
                        text: "文字列",
                        moreNumberFormat: "その他の表示形式...",
                        percentStyle: "パーセント スタイル",
                        commaStyle: "桁区切りスタイル",
                        increaseDecimal: "小数点以下の表示桁数を増やす",
                        decreaseDecimal: "小数点以下の表示桁数を減らす",
                        buttonCellType: "コマンドボタン",
                        checkboxCellType: "チェックボックス",
                        comboBoxCellType: "コンボボックス",
                        hyperlinkCellType: "ハイパーリンク",
                        clearCellType: "セル型を<br>クリア",
                        clearCellType1: "セル型をクリア",
                        conditionFormat: "条件付き<br>書式",
                        conditionFormat1: "条件付き書式",
                        formatTable: "テーブルとして<br>書式設定",
                        formatTable1: "テーブルとして書式設定",
                        insert: "挿入",
                        insertCells: "セルを挿入...",
                        insertSheetRows: "行の挿入",
                        insertSheetColumns: "列の挿入",
                        insertSheet: "シートの挿入",
                        Delete: "削除",
                        deleteCells: "セルを削除...",
                        deleteSheetRows: "行の削除",
                        deleteSheetColumns: "列の削除",
                        deleteSheet: "シートの削除",
                        format: "書式",
                        rowHeight: "行の高さ...",
                        autofitRowHeight: "行の高さの自動調整",
                        defaultHeight: "既定の高さ...",
                        columnWidth: "列の幅...",
                        autofitColumnWidth: "列の幅の自動調整",
                        defaultWidth: "既定の幅...",
                        hideRows: "行を表示しない",
                        hideColumns: "列を表示しない",
                        unHideRows: "行の再表示",
                        unHideColumns: "列の再表示",
                        protectSheet: "保護...",
                        unProtectSheet: "保護の解除...",
                        lockCells: "セルのロック",
                        unLockCells: "セルのロック解除",
                        autoSum: "オート SUM",
                        sum: "合計",
                        average: "平均",
                        countNumbers: "数値の個数",
                        max: "最大値",
                        min: "最小値",
                        fill: "フィル",
                        down: "下方向へコピー",
                        right: "右方向へコピー",
                        up: "上方向へコピー",
                        left: "左方向へコピー",
                        series: "連続データの作成...",
                        clear: "クリア",
                        clearAll: "すべてクリア",
                        clearFormat: "書式のクリア",
                        clearContent: "数式と値のクリア",
                        clearComments: "コメントのクリア",
                        sortFilter: "ソートと<br>フィルター",
                        sortFilter1: "ソートとフィルター",
                        sortAtoZ: "昇順",
                        sortZtoA: "降順",
                        customSort: "ユーザー設定のソート...",
                        filter: "フィルター",
                        clearFilter: "クリア",
                        reapply: "再適用",
                        find: "検索",
                        find1: "検索...",
                        goto: "ジャンプ..."
                    },
                    insert: {
                        insert: "挿入",
                        table: "テーブル",
                        sparklines: "スパークライン",
                        line: "折れ線",
                        column: "縦棒",
                        winloss: "勝敗",
                        insertTable: "テーブルの挿入",
                        insertLineSparkline: "折れ線スパークラインの挿入",
                        insertColumnSparkline: "縦棒スパークラインの挿入",
                        insertWinlossSparkline: "勝敗スパークラインの挿入",
                        picture: "画像",
                        illustrations: "図",
                        insertPicture: "図の挿入",
                        insertPieSparkline: "円スパークラインの挿入",
                        insertAreaSparkline: "面スパークラインの挿入",
                        insertScatterSparkline: "散布図スパークラインの挿入",
                        pie: "円",
                        area: "面",
                        scatter: "散布図",
                        insertBulletSparkline: "ブレットスパークラインの挿入",
                        bullet: "ブレット",
                        insertSpreadSparkline: "スプレッドスパークラインの挿入",
                        spread: "スプレッド",
                        insertStackedSparkline: "積み上げスパークラインの挿入",
                        stacked: "積み上げ",
                        insertHbarSparkline: "水平バースパークラインの挿入",
                        hbar: "水平バー",
                        insertVbarSparkline: "垂直バースパークラインの挿入",
                        vbar: "垂直バー",
                        insertVariSparkline: "バリスパークラインの挿入",
                        variance: "バリ",
                        insertBoxPlotSparkline: "ボックスプロットスパークラインの挿入",
                        boxplot: "ボックス<br>プロット",
                        insertCascadeSparkline: "カスケードスパークラインの挿入",
                        cascade: "カスケード",
                        insertParetoSparkline: "パレートスパークラインの挿入",
                        pareto: "パレート"
                    },
                    formulas: {
                        formulas: "数式",
                        insertFunction: "関数の<br>挿入",
                        insertFunction1: "関数の挿入",
                        functions: "関数",
                        names: "名前",
                        nameManager: "名前の<br>管理",
                        nameManager1: "名前の管理"
                    },
                    data: {
                        data: "データ",
                        sortFilter: "ソートとフィルター",
                        dataTools: "データツール",
                        outline: "アウトライン",
                        sortAtoZ: "昇順",
                        sortZtoA: "降順",
                        sort: "ソート",
                        customSort: "ユーザー設定のソート...",
                        filter: "フィルター",
                        clear: "クリア",
                        clearFilter: "フィルターのクリア",
                        reapply: "再適用",
                        dataValidation: "データの<br>入力規則",
                        dataValidation1: "データの入力規則",
                        circleInvalidData: "無効データのマーク",
                        clearInvalidCircles: "入力規則マークのクリア",
                        group: "グループ化",
                        unGroup: "グループ解除",
                        showDetail: "詳細データの表示",
                        hideDetail: "詳細を表示しない",
                        designMode: "デザインモード",
                        enterTemplate: "テンプレートデザインモードを開始します",
                        template: "テンプレート",
                        bindingPath: "バインディングパス",
                        loadSchemaTitle: "スキーマの読込",
                        loadSchema: "スキーマの読込",
                        loadDataSourceFilter: {
                            json: "JSON ファイル(*.json)",
                            txt: "テキストファイル(*.txt)"
                        },
                        saveDataSourceFilter: {
                            json: "JSON ファイル(*.json)"
                        },
                        saveSchemaTitle: "スキーマの保存",
                        saveSchema: "スキーマの保存",
                        autoGenerateColumns: "列の自動出力",
                        columns: "Columns",
                        name: "Name",
                        details: "詳細",
                        ok: "OK",
                        cancel: "キャンセル",
                        loadDataError: "ファイルを読み込んでください。",
                        addNode: "ノードの追加",
                        remove: "削除",
                        rename: "名前の変更",
                        table: "テーブル",
                        selectOptions: "オプション",
                        clearBindingPath: "バインディングパスのクリア",
                        dataField: "データフィールド",
                        warningTable: "列数が変更されます。続けますか？",
                        warningDataField: "\"autoGenerateColumns\"をOFFにしてデータフィールドを設定しますか？",
                        checkbox: "チェックボックス",
                        hyperlink: "ハイパーリンク",
                        combox: "コンボボックス",
                        button: "ボタン",
                        text: "テキスト",
                        autoGenerateLabel: "ラベルの自動出力",
                        autoGenerateLabelTip: "データラベルを自動で出力します",
                        unallowableTableBindingTip: "データフィールドはテーブル上にのみ設定可能です。テーブルを選択してください。",
                        overwriteCellTypeWarning: "シート内にある複数のセル型が変更または上書きされます。続けますか？",
                        removeNodeWarning: "削除しようとしているノードには複数の子ノードがあります。本当に削除しますか？",
                        unallowComboxBindingTip: "コンボボックスのアイテムはコンボボックスにのみ設定可能です。コンボボックスを選択してください。"
                    },
                    view: {
                        view: "表示",
                        showHide: "表示",
                        zoom: "ズーム",
                        viewport: "ウインドウ",
                        rowHeader: "行ヘッダ",
                        columnHeader: "列ヘッダ",
                        verticalGridline: "垂直グリッド線",
                        horizontalGridline: "水平グリッド線",
                        tabStrip: "タブストリップ",
                        newTab: "新しいタブ",
                        rowHeaderTip: "行ヘッダを表示",
                        columnHeaderTip: "列ヘッダを表示",
                        verticalGridlineTip: "垂直グリッド線を表示",
                        horizontalGridlineTip: "水平グリッド線を表示",
                        tabStripTip: "タブストリップを表示",
                        newTabTip: "新しいタブを表示",
                        zoomToSelection: "選択範囲に合わせて<br>拡大/縮小",
                        zoomToSelection1: "選択範囲に合わせて拡大/縮小",
                        freezePane: "ウインドウ枠<br>の固定",
                        freezePane1: "ウインドウ枠の固定",
                        freezeTopRow: "先頭行の固定",
                        freezeFirstColumn: "先頭列の固定",
                        freezeBottomRow: "末尾行の固定",
                        freezeLastColumn: "末尾列の固定",
                        unFreezePane: "ウインドウ枠<br>固定の解除",
                        unFreezePane1: "ウインドウ枠固定の解除"
                    },
                    setting: {
                        setting: "設定",
                        spreadSetting: "Spread 設定",
                        sheetSetting: "シート",
                        general: "全般",
                        generalTip: "全般",
                        scrollBars: "スクロールバー",
                        tabStrip: "タブストリップ",
                        gridLines: "グリッド線",
                        calculation: "計算",
                        headers: "ヘッダ"
                    },
                    sparkLineDesign: {
                        design: "スパークライン",
                        type: "種類",
                        show: "表示",
                        style: "スタイル",
                        groups: "グループ",
                        line: "折れ線",
                        column: "縦棒",
                        winLoss: "勝敗",
                        lineTip: "折れ線スパークラインに変換",
                        columnTip: "縦棒スパークラインに変換",
                        winLossTip: "勝敗スパークラインに変換",
                        highPoint: "頂点（山）",
                        lowPoint: "頂点（谷）",
                        negativePoint: "負のポイント",
                        firstPoint: "始点",
                        lastPoint: "終点",
                        markers: "マーカー",
                        highPointTip: "スパークラインの頂点（山）の表示/非表示",
                        lowPointTip: "スパークラインの頂点（谷）の表示/非表示",
                        negativePointTip: "スパークラインの負のポイントの表示/非表示",
                        firstPointTip: "スパークラインの始点の表示/非表示",
                        lastPointTip: "スパークラインの終点の表示/非表示",
                        markersTip: "スパークラインのマーカーの表示/非表示",
                        sparklineColor: "スパークラインの色",
                        markerColor: "マーカーの色",
                        sparklineWeight: "太さ",
                        customWeight: "ユーザー設定の太さ...",
                        group: "グループ化",
                        groupTip: "選択したスパークラインのグループ化",
                        unGroupTip: "選択したスパークラインのグループ解除",
                        unGroup: "グループ解除",
                        clear: "クリア",
                        clearSparkline: "選択したスパークラインのクリア",
                        clearSparklineGroup: "選択したスパークライングループのクリア"
                    },
                    formulaSparklineDesign: {
                        design: "デザイン",
                        argument: "引数",
                        settings: "設定"
                    },
                    tableDesign: {
                        design: "デザイン",
                        tableName: "テーブル名",
                        resizeTable: "テーブルのサイズ変更",
                        tableOption: "テーブル スタイルのオプション",
                        property: "プロパティ",
                        headerRow: "見出し行",
                        totalRow: "集計行",
                        bandedRows: "縞模様（行）",
                        firstColumn: "最初の列",
                        lastColumn: "最後の列",
                        bandedColumns: "縞模様（列）",
                        filterButton: "フィルタボタン",
                        tableStyle: "テーブル スタイル",
                        style: "スタイル",
                        tools: "Tools",
                        insertSlicer: "Insert Slicer"
                    },
                    fontFamilies: {
                        ff1: { name: "Arial", text: "Arial" },
                        ff2: { name: "Arial Black", text: "Arial Black" },
                        ff3: { name: "Calibri", text: "Calibri" },
                        ff4: { name: "Cambria", text: "Cambria" },
                        ff5: { name: "Candara", text: "Candara" },
                        ff6: { name: "Century", text: "Century" },
                        ff7: { name: "'Courier New'", text: "Courier New" },
                        ff8: { name: "'Comic Sans MS'", text: "Comic Sans MS" },
                        ff9: { name: "Garamond", text: "Garamond" },
                        ff10: { name: "Georgia", text: "Georgia" },
                        ff11: { name: "'Malgun Gothic'", text: "Malgun Gothic" },
                        ff12: { name: "Mangal", text: "Mangal" },
                        ff13: { name: "Tahoma", text: "Tahoma" },
                        ff14: { name: "Times", text: "Times" },
                        ff15: { name: "'Times New Roman'", text: "Times New Roman" },
                        ff16: { name: "'Trebuchet MS'", text: "Trebuchet MS" },
                        ff17: { name: "Verdana", text: "Verdana" },
                        ff18: { name: "Wingdings", text: "Wingdings" },
                        ff19: { name: "Meiryo, メイリオ", text: "メイリオ" },
                        ff20: { name: "'MS Gothic', 'ＭＳ ゴシック'", text: "ＭＳ ゴシック" },
                        ff21: { name: "'MS Mincho', 'ＭＳ 明朝'", text: "ＭＳ 明朝" },
                        ff22: { name: "'MS PGothic', 'ＭＳ Ｐゴシック'", text: "ＭＳ Ｐゴシック" },
                        ff23: { name: "'MS PMincho', 'ＭＳ Ｐ明朝'", text: "ＭＳ Ｐ明朝" }
                    },
                    slicerOptions: {
                        options: "OPTIONS",
                        slicerCaptionShow: "Slicer Caption:",
                        slicerCaption: "Slicer Caption",
                        slicerSettings: "Slicer Settings",
                        slicer: "Slicer",
                        styles: "Styles",
                        slicerStyles: "Slicer Styles",
                        columnsShow: "Columns:",
                        heightShow: "Height:",
                        widthShow: "Width:",
                        columns: "Columns:",
                        height: "Height:",
                        width: "Width:",
                        buttons: "Buttons",
                        size: "Size",
                        shapeHeight: "Shape Height",
                        shapeWidth: "Shape Width"
                    }
                };

                jp_res.seriesDialog = {
                    series: "連続データ",
                    seriesIn: "範囲",
                    rows: "行",
                    columns: "列",
                    type: "種類",
                    linear: "加算",
                    growth: "乗算",
                    date: "日付",
                    autoFill: "オートフィル",
                    dateUnit: "増加単位",
                    day: "日",
                    weekday: "週日",
                    month: "月",
                    year: "年",
                    trend: "データ予測",
                    stepValue: "増分値",
                    stopValue: "停止値"
                };

                jp_res.customSortDialog = {
                    sort: "並べ替え",
                    addLevel: "レベルの追加",
                    deleteLevel: "レベルの削除",
                    copyLevel: "レベルのコピー",
                    options: "オプション...",
                    sortBy: "列",
                    sortBy2: "最優先",
                    thenBy: "次に優先",
                    sortOn: "並べ替えのキー",
                    sortOrder: "順序",
                    sortOptions: "並べ替えオプション",
                    orientation: "方向",
                    sortTopToBottom: "行単位",
                    sortLeftToRight: "列単位"
                };

                jp_res.createTableDialog = {
                    createTable: "テーブルの作成",
                    whereYourTable: "テーブルに変換するデータ範囲を指定してください"
                };

                jp_res.createSparklineDialog = {
                    createSparkline: "スパークラインの作成",
                    dataRange: "データ範囲",
                    locationRange: "参照範囲",
                    chooseData: "データを選択してください",
                    chooseWhere: "スパークラインを配置する場所を選択してください",
                    warningText: "場所の参照が有効ではありません。すべてのセルが同じ行または列に含まれていません。すべてのセルが 1 つの行または列に含まれるように選択してください。"
                };

                jp_res.dataValidationDialog = {
                    dataValidation: "データの入力規則",
                    settings: "設定",
                    validationCriteria: "条件の設定",
                    allow: "入力値の種類",
                    anyValue: "すべての値",
                    wholeNumber: "整数",
                    decimal: "小数点数",
                    list: "リスト",
                    date: "日付",
                    textLength: "文字列（長さ指定）",
                    custom: "ユーザー設定",
                    data: "データ",
                    dataLabel: "データ:",
                    between: "次の値の間",
                    notBetween: "次の間の値以外",
                    equalTo: "次の値に等しい",
                    notEqualTo: "次の値に等しくない",
                    greaterThan: "次の値より大きい",
                    lessThan: "次の値より小さい",
                    greaterEqual: "次の値以上",
                    lessEqual: "次の値以下",
                    minimum: "最小値:",
                    maximum: "最大値:",
                    value: "値:",
                    length: "長さ:",
                    startDate: "次の日付から:",
                    endDate: "次の日付まで:",
                    source: "元の値:",
                    formula: "数式:",
                    dateLabel: "日付:",
                    ignoreBlank: "空白を無視する",
                    inCellDropDown: "ドロップダウンリストから選択する",
                    inputMessage: "入力時メッセージ",
                    errorAlert: "エラーメッセージ",
                    showMessage: "セルの選択時にメッセージを表示する",
                    showMessage2: "セルの選択時に表示するメッセージ: ",
                    title: "タイトル",
                    showError: "無効なデータが表示されたらエラーメッセージを表示する",
                    showError2: "無効なデータ入力されたときに表示するエラーメッセージ:",
                    style: "スタイル:",
                    stop: "停止",
                    warning: "注意",
                    information: "情報",
                    errorMessage: "エラーメッセージ",
                    clearAll: "すべてクリア",
                    valueEmptyMessage: "値を入力してください。",
                    minimumMaximumMessage: "最大値 は 最小値 以上の値でなければなりません。",
                    errorMessage1: "入力した値は正しくありません。\r\n ユーザーの設定によって、セルに入力できる値が制限されています。",
                    errorMessage2: "入力した値は正しくありません。\r\n ユーザーの設定によって、セルに入力できる値が制限されています。\r\n続けますか？"
                };

                jp_res.spreadSettingDialog = {
                    spreadSetting: "Spread 設定",
                    general: "全般",
                    settings: "設定",
                    allowDragDrop: "ドラッグドロップを有効にする",
                    allowFormula: "数式の直接入力を許可する",
                    allowDragFill: "ドラッグフィルを有効にする",
                    allowZoom: "ズームを有効にする",
                    allowUndo: "アンドゥ操作を有効にする",
                    allowOverflow: "テキストのオーバーフローを有効にする",
                    scrollBars: "スクロールバー",
                    visibility: "表示",
                    verticalScrollBar: "垂直スクロールバー",
                    horizontalScrollBar: "水平スクロールバー",
                    scrollbarShowMax: "行/列数を最大としてボタン位置を表示",
                    scrollbarMaxAlign: "行/列数を最大としてバー領域を表示",
                    tabStrip: "タブストリップ",
                    tabStripVisible: "タブストリップの表示",
                    tabStripEditable: "編集を許可する",
                    newTabVisible: "新しいタブを表示",
                    tabStripRatio: "表示比率"
                };

                jp_res.sheetSettingDialog = {
                    sheetSetting: "シートの設定",
                    general: "全般",
                    columnCount: "列数",
                    rowCount: "行数",
                    frozenColumnCount: "固定列数の設定",
                    frozenRowCount: "固定行数の設定",
                    trailingFrozenColumnCount: "固定列数（末尾列）の設定",
                    trailingFrozenRowCount: "固定行数（末尾行）の設定",
                    selectionPolicy: "オペレーションモード",
                    singleSelection: "単一選択モード",
                    rangeSelection: "複数選択モード",
                    multiRangeSelection: "拡張選択モード",
                    protect: "シートの保護を有効にする",
                    gridlines: "グリッド線",
                    horizontalGridline: "水平グリッド線",
                    verticalGridline: "垂直グリッド線",
                    gridlineColor: "色",
                    calculation: "計算",
                    referenceStyle: "参照スタイル",
                    a1: "A1式",
                    r1c1: "R1C1式",
                    headers: "ヘッダ",
                    columnHeaders: "列",
                    rowHeaders: "行",
                    columnHeaderRowCount: "列ヘッダの行数",
                    columnHeaderAutoText: "列ヘッダの自動テキスト",
                    columnHeaderAutoIndex: "列ヘッダの自動インデックス",
                    defaultRowHeight: "列ヘッダ行の高さ",
                    columnHeaderVisible: "列ヘッダを表示する",
                    blank: "空白",
                    numbers: "数字",
                    letters: "文字",
                    rowHeaderColumnCount: "行ヘッダの列数",
                    rowHeaderAutoText: "行ヘッダの自動テキスト",
                    rowHeaderAutoIndex: "行ヘッダの自動インデックス",
                    defaultColumnWidth: "行ヘッダ列の幅",
                    rowHeaderVisible: "行ヘッダを表示する",
                    sheetTab: "シートタブ",
                    sheetTabColor: "シートタブの色"
                };

                jp_res.groupDirectionDialog = {
                    settings: "設定",
                    direction: "集計行または列の位置",
                    rowDirection: "詳細データの下",
                    columnDirection: "詳細データの右"
                };

                jp_res.insertSparklineDialog = {
                    createSparklines: "スパークラインの作成",
                    dataRange: "データ範囲:",
                    dataRangeTitle: "データを選択してください",
                    locationRange: "参照範囲",
                    locationRangeTitle: "スパークラインを配置する場所を選択してください",
                    errorDataRangeMessage: "参照が正しくありません",
                    isFormulaSparkline: "数式形式で設定"
                };

                jp_res.sparklineWeightDialog = {
                    sparklineWeight: "スパークラインの太さ",
                    inputWeight: "スパークラインの太さを入力してください",
                    errorMessage: "入力した値は正しくありません。"
                };

                jp_res.sparklineMarkerColorDialog = {
                    sparklineMarkerColor: "マーカーの色:",
                    negativePoints: "負のポイント:",
                    markers: "マーカー:",
                    highPoint: "頂点（山）:",
                    lowPoint: "頂点（谷）:",
                    firstPoint: "始点:",
                    lastPoint: "終点:"
                };

                jp_res.resizeTableDialog = {
                    title: "テーブルのサイズ変更",
                    dataRangeTitle: "テーブルに変換する新しいデータ範囲を指定してください:",
                    note: "メモ: テーブルの見出しの位置は変更できません。また、新しいテーブル範囲が元のテーブル範囲に重なるようにしてください。"
                };

                jp_res.statusBar = {
                    ready: "コマンド",
                    enter: "入力",
                    edit: "編集"
                };

                jp_res.pieSparklineDialog = {
                    percentage: "セル範囲または割合",
                    color: "色",
                    addColor: "色の追加",
                    pieSparklineSetting: "円スパークラインの設定"
                };

                jp_res.areaSparklineDialog = {
                    title: "面スパークラインの数式",
                    points: "セル範囲",
                    min: "最大値",
                    max: "最小値",
                    line1: "線1",
                    line2: "線2",
                    positiveColor: "正の値の色",
                    negativeColor: "負の値の色",
                    areaSparklineSetting: "面スパークラインの設定"
                };

                jp_res.scatterSparklineDialog = {
                    points1: "系列1",
                    points2: "系列2",
                    minX: "X軸の最小値",
                    maxX: "X軸の最大値",
                    minY: "Y軸の最小値",
                    maxY: "Y軸の最大値",
                    hLine: "水平線の位置",
                    vLine: "垂直線の位置",
                    xMinZone: "範囲 - Xの最小値",
                    yMinZone: "範囲 - Yの最小値",
                    xMaxZone: "範囲 - Xの最大値",
                    yMaxZone: "範囲 - Yの最大値",
                    tags: "タグ",
                    drawSymbol: "シンボルの描画",
                    drawLines: "線の描画",
                    color1: "色1",
                    color2: "色2",
                    dash: "点線",
                    scatterSparklineSetting: "散布図スパークラインの設定"
                };

                jp_res.compatibleSparklineDialog = {
                    title: "スパークラインの数式",
                    style: "スタイル",
                    show: "表示",
                    group: "グループ",
                    data: "セル範囲",
                    dataOrientation: "データの方向",
                    dateAxisData: "軸データのセル範囲",
                    dateAxisOrientation: "軸データの方向",
                    settting: "設定",
                    axisColor: "軸",
                    firstMarkerColor: "マーカー（最初）",
                    highMarkerColor: "マーカー（最高値）",
                    lastMarkerColor: "マーカー（最後）",
                    lowMarkerColor: "マーカー（最小値）",
                    markersColor: "マーカー（通常）",
                    negativeColor: "負の値",
                    seriesColor: "系列",
                    displayXAxis: "X軸を表示",
                    showFirst: "最初のデータを表示",
                    showHigh: "最高値のデータを表示",
                    showLast: "最後のデータを表示",
                    showLow: "最小値のデータを表示",
                    showNegative: "負のデータを表示",
                    showMarkers: "マーカーを表示",
                    lineWeight: "線の太さ",
                    displayHidden: "非表示行および列のデータを表示する",
                    displayEmptyCellsAs: "空白セルデータの表示方法",
                    rightToLeft: "右から左",
                    minAxisType: "軸の形式（最小値）",
                    maxAxisType: "軸の形式（最大値）",
                    manualMax: "最大値",
                    manualMin: "最小値",
                    gaps: "描画しない",
                    zero: "ゼロ",
                    connect: "次のデータに接続",
                    vertical: "垂直",
                    horizontal: "水平",
                    stylesetting: "スタイルの設定",
                    individual: "固有",
                    custom: "カスタム",
                    compatibleSparklineSetting: "スパークラインの設定",
                    styleSetting: "スタイルの設定",
                    errorMessage: "正しい範囲を入力してください。"
                };

                jp_res.bulletSparklineDialog = {
                    bulletSparklineSetting: "ブレットスパークラインの設定",
                    measure: "バーの長さ",
                    target: "目標線の位置",
                    maxi: "最大値",
                    good: "良好値",
                    bad: "不十分値",
                    forecast: "予測線の位置",
                    tickunit: "目盛単位",
                    colorScheme: "表示色",
                    vertical: "垂直表示"
                };

                jp_res.spreadSparklineDialog = {
                    spreadSparklineSetting: "スプレッドスパークラインの設定",
                    points: "対象セル範囲",
                    showAverage: "平均の表示",
                    scaleStart: "下限値",
                    scaleEnd: "上限値",
                    style: "スタイル",
                    colorScheme: "表示色",
                    vertical: "垂直表示",
                    stacked: "積み上げ",
                    spread: "スプレッド",
                    jitter: "ランダムなドット",
                    poles: "ポール",
                    stackedDots: "積み上げドット",
                    stripe: "ストライプ"
                };

                jp_res.stackedSparklineDialog = {
                    stackedSparklineSetting: "積み上げスパークライン",
                    points: "値のセル範囲",
                    colorRange: "色のセル範囲",
                    labelRange: "ラベルのセル範囲",
                    maximum: "最大値",
                    targetRed: "赤色線の位置",
                    targetGreen: "緑色線の位置",
                    targetBlue: "青色線の位置",
                    targetYellow: "黄色線の位置",
                    color: "既定の色",
                    highlightPosition: "ハイライトの位置",
                    vertical: "垂直表示",
                    textOrientation: "ラベルテキストの向き",
                    textSize: "ラベルテキストのサイズ",
                    textHorizontal: "水平",
                    textVertical: "垂直",
                    px: "px"
                };

                jp_res.barbaseSparklineDialog = {
                    hbarSparklineSetting: "水平バースパークラインの設定",
                    vbarSparklineSetting: "垂直バースパークラインの設定",
                    value: "値",
                    colorScheme: "表示色"
                };

                jp_res.variSparklineDialog = {
                    variSparklineSetting: "バリスパークラインの設定",
                    variance: "バーの長さ",
                    reference: "参照線の位置",
                    mini: "最小値",
                    maxi: "最大値",
                    mark: "マーク線の位置",
                    tickunit: "目盛単位",
                    legend: "テキスト表示の有無",
                    colorPositive: "正の色",
                    colorNegative: "負の色",
                    vertical: "垂直表示"
                };

                jp_res.boxplotSparklineDialog = {
                    boxplotSparklineSetting: "ボックスプロットスパークラインの設定",
                    points: "対象セル範囲",
                    boxPlotClass: "クラス",
                    showAverage: "平均の表示",
                    scaleStart: "下限値",
                    scaleEnd: "上限値",
                    acceptableStart: "許容開始位置",
                    acceptableEnd: "許容終了位置",
                    colorScheme: "表示色",
                    style: "スタイル",
                    vertical: "垂直表示",
                    fiveNS: "五数要約",
                    sevenNS: "七数要約",
                    tukey: "Tukey",
                    bowley: "Bowley",
                    sigma: "Sigma3",
                    classical: "Classical",
                    neo: "Neo"
                };

                jp_res.cascadeSparklineDialog = {
                    cascadeSparklineSetting: "カスケードスパークラインの設定",
                    pointsRange: "対象セル範囲",
                    pointIndex: "インデックス",
                    labelsRange: "ラベルのセル範囲",
                    minimum: "最小値",
                    maximum: "最大値",
                    colorPositive: "正の値",
                    colorNegative: "負の値",
                    vertical: "垂直表示"
                };
                jp_res.multiCellFormula = {
                    warningText: "選択範囲は異なるタイプの数式を含んでいます。新しい範囲を選択しています。"
                };

                jp_res.paretoSparklineDialog = {
                    paretoSparklineSetting: "パレートスパークラインの設定",
                    points: "値のセル範囲",
                    pointIndex: "インデックス",
                    colorRange: "色のセル範囲",
                    target: "目盛線の位置",
                    target2: "第二目盛線の位置",
                    highlightPosition: "ハイライトの位置",
                    label: "ラベル",
                    vertical: "垂直表示",
                    none: "なし",
                    cumulated: "累積",
                    single: "単一"
                };

                jp_res.sliderPanel = {
                    title: "フィールドリスト"
                };

                jp_res.protectionOptionDialog = {
                    title: "Protect Sheet",
                    label: "Allow all users of this worksheet to:",
                    allowSelectLockedCells: "Select locked cells",
                    allowSelectUnlockedCells: "Select unlocked cells",
                    allowSort: "Sort",
                    allowFilter: "Use AutoFilter",
                    allowResizeRows: "Resize rows",
                    allowResizeColumns: "Resize columns",
                    allowEditObjects: "Edit objects"
                };
                jp_res.activateToolNotFound = "The activate tool can not be found, please reinstall SpreadJS Designer then try again.";

                jp_res.insertSlicerDialog = {
                    insertSlicer: "Insert Slicer"
                };

                jp_res.formatSlicerStyle = {
                    custom: "Custom",
                    light: "Light",
                    dark: "Dark",
                    other: "Other",
                    newSlicerStyle: "New Slicer Style...",
                    slicerStyle: "Slicer Style",
                    name: "Name",
                    slicerElement: "Slicer Element",
                    format: "Format",
                    clear: "Clear",
                    preview: "Preview",
                    exception: "This style name is already exists."
                };

                jp_res.slicerElement = {
                    wholeSlicer: "Whole Slicer",
                    header: "Header",
                    selectedItemWithData: "Selected Item With Data",
                    selectedItemWithNoData: "Selected Item With No Data",
                    unselectedItemWithData: "Unselected Item With Data",
                    unselectedItemWithNoData: "Unselected Item With No Data",
                    hoveredSelectedItemWithData: "Hover Selected Item With Data",
                    hoveredSelectedItemWithNoData: "Hover Selected Item With No Data",
                    hoveredUnselectedItemWithData: "Hover Unselected Item With Data",
                    hoveredUnselectedItemWithNoData: "Hover Unselected Item With No Data"
                };

                jp_res.slicerSettingDialog = {
                    slicerSetting: "Slicer Settings",
                    sourceName: "Source Name:",
                    name: "Name:",
                    header: "Header",
                    display: "Display header",
                    caption: "Caption:",
                    items: "Item Sorting and Filtering",
                    ascending: "Ascending(A to Z)",
                    descending: "Descending(Z to A)",
                    customList: "Use Custom Lists when sorting",
                    hideItem: "Hide items with no data",
                    visuallyItem: "Visually indicate items with no data",
                    showItem: "Show items with no data last"
                };

                jp_res.slicerPropertyDialog = {
                    formatSlicer: "Format Slicer",
                    position: "Position and Layout",
                    size: "Size",
                    properties: "Properties",
                    pos: "Position",
                    horizontal: "Horizontal:",
                    vertial: "Vertial:",
                    disableResizingMoving: "Disable resizing and moving",
                    layout: "Layout",
                    numberColumn: "Number of columns:",
                    buttonHeight: "Button height:",
                    buttonWidth: "Button width:",
                    height: "Height:",
                    width: "Width:",
                    scaleHeight: "Scale Height:",
                    scaleWidth: "Scale Width:",
                    moveSize: "Move and size with cells",
                    moveNoSize: "Move and don't size with cells",
                    noMoveSize: "Don't move and size with cells",
                    locked: "Locked"
                };

                GcSpread.Sheets.designer.res = GcSpread.Sheets.designer.jp_res;
            })(designer.jp_res || (designer.jp_res = {}));
            var jp_res = designer.jp_res;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(GcSpread.Sheets || (GcSpread.Sheets = {}));
    var Sheets = GcSpread.Sheets;
})(GcSpread || (GcSpread = {}));
