"use strict";

$("#bigCategory").change(() => {
	let id = $("#bigCategory").val();
	console.log(id);

	$.ajax({
		url: "http://localhost:8080/mercari/add/medium",
		type: 'POST',
		dataType: 'json',
		data: {
			id: id
		},
		async: true,
	}).done(function(data) {
		console.log(data);
		let $el = $("#mediumCategory");
		let $el2 = $("#smallCategory");
		$el.empty();
		$el2.empty();

		$el.append($("<option>-- medium category --</option>"));
		$el2.append($("<option>-- medium categoryを選択してください --</option>"));
		$.each(data, function(key, value) {
			$el.append($("<option></option>")
				.attr("value", value.id).text(value.name));
		})
	});
});


$("#mediumCategory").change(() => {
	let id = $("#mediumCategory").val();
	console.log("medium id : " + id);
	$.ajax({
		url: "http://localhost:8080/mercari/add/small",
		type: 'POST',
		dataType: 'json',
		data: {
			id: id
		},
		async: true,
	}).done(function(data) {
		console.log("small data", data);
		let $el = $("#smallCategory");
		$el.empty();

		$el.append($("<option>-- small category--</option>"));
		$.each(data, function(key, value) {
			$el.append($("<option></option>")
				.attr("value", value.id).text(value.name));
		})
	});		
});