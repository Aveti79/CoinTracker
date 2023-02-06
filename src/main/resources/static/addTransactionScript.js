let eventFire = false;
const $currency = $(".currency");
const currentFiats = ['chf', 'eur', 'gbp', 'pln', 'usd'];

$currency.focusin(function () {
    this.value = '';
    $(this).next().val(this.value);
});

$currency.autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "coinNameAutocomplete",
            data: {term: request.term},
            success: function (data) {
                response($.map(data, function (item) {
                    if (item[4] === null) {
                        if (item[3] === 'FIAT' && currentFiats.includes(item[0])) {
                            item[4] = "icons/" + item[0] + "_icon.webp";
                        } else {
                            item[4] = "icons/blank_icon.webp";
                        }
                    }
                    return {
                        label: item[2].toUpperCase() + ' ' + item[1] + ' (' + item[3] + ')',
                        value: item[0],
                        logo: item[4]
                    };
                }))
            }
        });
    },
    minLength: 1,
    select: function (event, ui) {
        this.value = ui.item.label;
        $(this).next().val(ui.item.value)
        eventFire = true;
        return false;
    },
    change: function () {
        if (!eventFire) {
            $(this).next().val(this.value);
        }
        eventFire = false;
    }
});

$currency.each(function () {
    $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
        return $('<li>')
            .append('<div>' + '<img src="' + item.logo + '" alt=""/>' +
                '<span>' + item.label + '</span>' + '</div>')
            .appendTo(ul);
    }
});