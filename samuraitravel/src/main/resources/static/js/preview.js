//フィールドの値を変数宣言
const imageInput = document.getElementById('imageFile');
//idにimagePreviewが指定されている要素を変数宣言
const imagePreview = document.getElementById('imagePreview');
//imageInputの画像が変更されるたびイベント処理を実施する
imageInput.addEventListener('change', ()=>{
	//選択された画像ファイルが正常に読み込まれたらidにimagePreviewが指定されているHTML要素の中に読み込まれた画像ファイルを表示するためのimg要素を挿入する
	if (imageInput.files[0]) {
		let fileReader = new FileReader();
		fileReader.onload = () => {
			imagePreview.innerHTML = `<img src="${fileReader.result}" class="mb-3">`;
		}
		fileReader.readAsDataURL(imageInput.files[0]);
		//選択された画像ファイルが無い場合idにimagePreviewが指定されているHTML要素の中身を空にする
	} else {
		imagePreview.innerHTML = '';
	}
})