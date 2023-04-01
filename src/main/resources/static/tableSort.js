function sortTableByColumn(table, column, sortingOrder=true) {
    //Declaring sorting order (Ascending or Descending)
    const sortingDirection = sortingOrder ? 1 : -1;
    //Selecting table body
    const tableBody = table.tBodies[0];
    //After selecting table body, retrieves all rows of table into array
    const tableRows = Array.from(tableBody.querySelectorAll("tr"));

    //Sorting rows by using JS method .sort with custom sorter
    const sortedRows = tableRows.sort((a, b) => {
        let aColumnText;
        let bColumnText;
        if (Object.is(column, 2)) {
            aColumnText = a.querySelector(`td:nth-child(${column})`).textContent.trim();
            bColumnText = b.querySelector(`td:nth-child(${column})`).textContent.trim();
        } else if (Object.is(column, 1)) {
            aColumnText = parseInt(a.querySelector(`td:nth-child(${column})`).textContent.trim());
            bColumnText = parseInt(b.querySelector(`td:nth-child(${column})`).textContent.trim());
        } else if (Array.of(3,7,8).includes(column)) {
            aColumnText = parseFloat(a.querySelector(`td:nth-child(${column})`).textContent.trim().replaceAll(" ", "").replace("$",""));
            bColumnText = parseFloat(b.querySelector(`td:nth-child(${column})`).textContent.trim().replaceAll(" ", "").replace("$",""));
        } else if (Array.of(4,5,6).includes(column)) {
            aColumnText = parseFloat(a.querySelector(`td:nth-child(${column})`).textContent.trim().replace("%",""));
            bColumnText = parseFloat(b.querySelector(`td:nth-child(${column})`).textContent.trim().replace("%",""));
        }
        return aColumnText > bColumnText ? (1 * sortingDirection) : (-1 * sortingDirection);
    })
    //Removing all rows from old table
    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }
    //Re-adding now sorted rows to table
    tableBody.append(...sortedRows);

    //Remember how the table was sorted
    table.querySelectorAll("th").forEach(th => th.classList.remove("th-sort-asc", "th-sort-desc"));
    table.querySelector(`th:nth-child(${column})`).classList.toggle("th-sort-asc", sortingOrder);
    table.querySelector(`th:nth-child(${column})`).classList.toggle("th-sort-desc", !sortingOrder);
}

/*
* Make table sortable by column on click
* First select all sortable tables headers from document
* Then add "click" event listener to all selected headers
* Inside anonymous function of "click" prepare arguments to pass
* to sortTableByColumn() then call this method.
*/
document.querySelectorAll(".table-sortable th").forEach(headerCell => {
    headerCell.addEventListener("click", () => {
        const tableElement = headerCell.closest("table");
        const columnElement = Array.from(headerCell.parentElement.children).indexOf(headerCell);
        const sortingOrder = headerCell.classList.contains("th-sort-desc");
        sortTableByColumn(tableElement, columnElement + 1, sortingOrder);
    })
})
