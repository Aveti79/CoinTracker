function setLangCookie() {
    document.cookie = 'lang=en' //document.querySelector('html').getAttribute('lang');
}

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

function checkLangCookie() {
    let lang = getCookie("lang");
    let params = new URLSearchParams(location.search);
    if (lang === '') {
        setLangCookie();
    } else if (lang !== 'en' && !params.has('lang')) {
        //window.location.search = 'lang=' + lang;
    }
}

function checkLangSelected() {
    let languageOptions = document.querySelectorAll('.lang_select > label > select > option');
    let selectedLang = getCookie('lang');
    console.log(selectedLang);
    if (selectedLang !== '') {
        languageOptions.forEach((option) => {
            if (option.getAttribute('value') === selectedLang) {
                option.setAttribute('selected', 'true');
            }
        })
    }
}

function langChange(langSym) {
    document.cookie = 'lang=' + langSym;
    //let param = new URLSearchParams(location.search);
    //param.delete('lang');
    //param.append('lang',langSym);
    window.location.search = 'lang=' + langSym;
}

window.onload = () => {
    checkLangCookie();
    checkLangSelected();
}
