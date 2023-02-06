function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}


function checkLangSelected() {
    let languageOptions = document.querySelectorAll('.lang_select > label > select > option');
    let selectedLang = getCookie('lang');
    if (selectedLang !== '') {
        languageOptions.forEach((option) => {
            if (option.getAttribute('value') === selectedLang) {
                option.setAttribute('selected', 'true');
            }
        })
    }
}

function langChange(langSym) {
    window.location.search = 'lang=' + langSym;
}

window.onload = () => {
    checkLangSelected();
}
