function sortTable() {
	var table, switching, rows, i, n, shouldSwitch;
	var currentRow, nextRow;
	var strX, strY;
	var temp;

	table = document.getElementById("raceResultsTable");
	rows = table.rows;
	
	n = rows.length - 1;
	
	switching = true;

	while (switching) {
		switching = false;
		
		for (i = 1; i < n; i++) {
			shouldSwitch = false;

			strX = rows[i].getElementsByTagName("TD")[0].innerHTML;
			strY = rows[i + 1].getElementsByTagName("TD")[0].innerHTML;

			temp = Number.parseInt(strX);
			currentRow = !isNaN(temp) ? temp : 100;

			temp = Number.parseInt(strY);
			nextRow = !isNaN(temp) ? temp : 100;

			if (Number(currentRow) > Number(nextRow)) {
				shouldSwitch = true;
				break;
			}
		}

		if (shouldSwitch) {
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
		}
	}
}

$(document).ready(function() {
	sortTable();
});