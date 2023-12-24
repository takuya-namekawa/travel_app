
let maxDate = new Date();
//変数maxDateへ三カ月後の日付を代入する
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);
//対象となるセレクタを設定
flatpickr('#fromCheckinDateToCheckoutDate', {
	//カレンダーの範囲選択モードを有効にする
	mode: "range",
	//カレンダーの言語を日本語にする
	locale: 'ja',
	//カレンダーで選択できる最小の日付を当日にする
	minDate: 'today',
	//カレンダーで選択できる最大の日付を三カ月後にする
	maxDate: maxDate
});