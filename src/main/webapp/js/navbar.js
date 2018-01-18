let onCategoryChoice = function (list_item, category_id) {
    let form = document.getElementById('search-form');
    if (list_item !== null) {
        console.log("a");
        form.elements.namedItem('category').value = category_id;
        let selected_category = document.getElementById('selected_category').innerText = list_item.innerText;
    } else {
        form.elements.namedItem('category').value = '';
        let selected_category = document.getElementById('selected_category').innerText = "Tutte le categorie";
    }
};