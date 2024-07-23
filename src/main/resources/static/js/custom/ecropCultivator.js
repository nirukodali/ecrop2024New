$(document).ready(function() {

	$('.modal').on('hidden.bs.modal', function() {
		var index = $("#cultivatorIndex").val();
		var indexOE = $("#cultivatorIndexOE").val();

		$("#searchParam" + index).val('0');
		$("#aadharNo").val('');
		$("#ocName").val('');
		$("#fatherName").val('');
		$("#occupantExtent").val('');

		$("#searchParam" + indexOE).val('0');
		$("#occupantExtentOE").val('');
	})
	alertify.set('notifier', 'position', 'top-right');

	Number.prototype.round = function(p) {
		p = p || 10;
		return parseFloat(this.toFixed(p));
	};

	// Change the selector if needed
	var $table = $('table.scroll'),
		$bodyCells = $table.find('tbody tr:first').children(),
		colWidth;

	// Adjust the width of thead cells when window resizes
	$(window).resize(function() {
		// Get the tbody columns width array
		colWidth = $bodyCells.map(function() {
			return $(this).width();
		}).get();

		// Set the width of thead columns
		$table.find('thead tr').children().each(function(i, v) {
			$(v).width(colWidth[i]);
		});
	}).resize(); // Trigger resize handler

});




var sel = document.getElementById("searchParam"), text = document.getElementById("text");

function onUserTypeChange(index, selectedValue) {
	var cultivatorType = $("#cultivatorType" + index).val();
	var cultivatorTypeCcrc = $("#cultivatorType_Ccrc" + index).val();
	var data_src = $("#data_src" + index).val();

	if (selectedValue === '1') {
		document.getElementById("ccrcbtnId").style.display = "none";
		document.getElementById("ownerbtnId").style.display = "";
		if (cultivatorType == 'L') {
			$("#searchParam" + index).val('0');
			swal.fire("Already Registered as Enjoyer!", "", "warning");
			return false;
		} else if (cultivatorType == 'O') {
			$("#searchParam" + index).val('0');
			swal.fire("Already Registered as Owner!", "", "warning");
			return false;
		}
		else {


			var availableExtent = parseFloat($("#availableExtent" + index).val()).round(3);
			if (availableExtent > 0) {
				var originalExt = document.getElementById("anubhavadarExtent" + index).value;
				setModalValues(index, "O", "Owner", originalExt);
				$('#ownerOrEnjoyerModal').modal('show');
			} else {
				$("#searchParam" + index).val('0');
				swal.fire("No Available Extent", "", "warning");
			}
		}


	} else if (selectedValue === '2') {
		var availableExtent = parseFloat($("#availableExtent" + index).val()).round(3);
		if (availableExtent > 0) {
			setModalValues(index, "K", "Cultivator", availableExtent);
			$('#cultivatorModal').modal('show');
			$("#searchParam" + index).val('0');
		} else {
			$("#searchParam" + index).val('0');
			swal.fire("No Available Extent", "", "warning");
		}
	} else if (selectedValue === '3') {
		document.getElementById("ccrcbtnId").style.display = "none";
		document.getElementById("ownerbtnId").style.display = "";
		if (cultivatorType == 'L') {
			$("#searchParam" + index).val('0');
			swal.fire("Already Registered as Enjoyer!", "", "warning");
			return false;
		} else if (cultivatorType == 'O') {
			$("#searchParam" + index).val('0');
			swal.fire("Already Registered as Owner!", "", "warning");
			return false;
		}

		else {


			var availableExtent = parseFloat($("#availableExtent" + index).val()).round(3);
			if (availableExtent > 0) {
				var originalExt = document.getElementById("anubhavadarExtent" + index).value;
				setModalValues(index, "L", 'Enjoyer', originalExt);
				$('#ownerOrEnjoyerModal').modal('show');
			} else {
				$("#searchParam" + index).val('0');
				swal.fire("No Available Extent", "", "warning");
			}
		}
	}
	else if (selectedValue === '4') {
		//		var availableExtent =$("#availableExtent_Ccrc" + index).val();
		//		alert(availableExtent)
		document.getElementById("ccrcbtnId").style.display = "block";
		document.getElementById("ownerbtnId").style.display = "none";

		if (cultivatorTypeCcrc == 'C') {
			$("#searchId" + index).val('0');
			swal.fire("Already CCRC added!", "", "warning");
			return false;
		}
		var kh_no = document.getElementById("khNo_Ccrc" + index).value;
		var survey_no = document.getElementById("crSno_Ccrc" + index).value;
		var inputs = document.querySelectorAll('input[name="cr_vcode_owner"]');
		count = inputs.length;
		var abcd=0;
		var originalExt = 0;
		var avail = 0;
		for (var i = 0; i < count; i++) {
			if (kh_no == document.getElementById("khNo" + i).value && survey_no == document.getElementById("crSno" + i).value) {
				abcd=1;
					var extent=document.getElementById("anubhavadarExtent_Ccrc" + index).value;
				originalExt = extent;
				//				avail= (document.getElementById("availableCcrc" + i).value);
				avail = document.getElementById("availableExtent" + i).value;
			}
		}
		//		alert(avail+'---'+originalExt)
		if (abcd == 0) {
			$("#searchId" + index).val('0');
			swal.fire("Please Select Owner record Also", "", "warning");
			return false;
		}
		else if (avail <= 0) {
			$("#searchId" + index).val('0');
			swal.fire("No Available Extent for CCRC", "", "warning");
		}
		else if (avail > 0) {
			//			alert('else')
			$("#searchId" + index).val('0');
			setModal(index, "C", "CCRC", originalExt, avail);
			$('#ownerOrEnjoyerModal').modal('show');
		}
	}

	else {
		var msg = 'Already Booked for ';
		var Type = data_src;
		var drop = '';
		if (selectedValue == '5') {
			drop = 'ROFR;'
		}
		if (selectedValue == '6') {
			drop = 'Non WebLand;'
		}
		if (selectedValue == '7') {
			drop = 'USUS;'
		}
		msg += drop;
		var availableExtent = parseFloat($("#availableExtent" + index).val()).round(4);
		if (availableExtent > 0) {
			var originalExt = document.getElementById("anubhavadarExtent" + index).value;
			setModalValues(index, Type, drop, originalExt);

			$('#cultivatorROFRModal').modal('show');
			$("#searchParam" + index).val('0');
		} else {
			$("#searchParam" + index).val('0');
			swal.fire(msg, "", "warning");
			$('#cultivatorROFRModal').modal('hide');

		}
	}

}

function setModal(index, cultivatorType, roleType, originalExt, avail) {
	if (cultivatorType == 'C') {
		suffix = 'OE'
		var inputValue = $("#aadharNoCcrc" + index).val();
		var aadharDisplay = document.querySelector(".aadharDisplay");
		var aadharInput = document.querySelector(".aadharInput");
		aadharDisplay.style.display = "inline";
		aadharDisplay.textContent = inputValue;
		aadharInput.style.display = "none";
		$("#owner_tenant").val(cultivatorType);
		document.getElementById("aadharNo" + suffix).disabled = false;
		document.getElementById("occupantExtent" + suffix).disabled = true;
		$("#refBookingId").val($("#bookingId" + index).val());
	}
	$("#crSnoLabel" + suffix).text($("#crSno_Ccrc" + index).val());
	$("#khNoLabel" + suffix).text($("#khNo_Ccrc" + index).val());
	$("#ocNameLabel" + suffix).text($("#ocName_Ccrc" + index).val());
	$("#fatherNameLabel" + suffix).text($("#fatherName_Ccrc" + index).val());
	$("#aadharNoLabel" + suffix).text($("#aadharNo_Ccrc" + index).val());
	$("#cultivatorType" + suffix).val(cultivatorType);
	$("#orgextentLabelOE").val(originalExt);
	$("#orgextentLabelC").val(originalExt);
	$("#MobileOE").val('');
	$("#MobileOE").text('');
$("#orgextentLabelC").val(originalExt);
	// if (cultivatorType == 'O') {
	// $("#aadharNo" + suffix).text($("#aadharNo" + index).val());
	// }
	$("#cultivatorModalHeaderId" + suffix).text(roleType);
	$("#cultivatorModalFieldId1" + suffix).text(roleType);

	$("#ccrcModalHeaderId" + suffix).text(roleType);
	$("#ccrcModalFieldId1" + suffix).text(roleType);

	$("#crSno" + suffix).val($("#crSno_Ccrc" + index).val());
	$("#khNo" + suffix).val($("#khNo_Ccrc" + index).val());
	$("#part_key" + suffix).val($("#part_key_Ccrc" + index).val());
	$("#cr_vcode" + suffix).val($("#cr_vcode_Ccrc" + index).val());
	$("#cr_year" + suffix).val($("#cr_year_Ccrc" + index).val());
	$("#cr_season" + suffix).val($("#cr_season_Ccrc" + index).val());
	$("#cultivatorType" + suffix).val(cultivatorType);
	$("#extentLabel" + suffix).val(originalExt);


	if (cultivatorType == 'C') {
		$("#ocName" + suffix).val($("#ocName_Ccrc" + index).val());
		$("#fatherName" + suffix).val($("#fatherName_Ccrc" + index).val());
		$("#aadharNo" + suffix).val($("#aadharNo_Ccrc" + index).val());
		$("#bookingId" + suffix).val($("#bookingId_Ccrc" + index).val());
		$("#aadharNo1" + suffix).val($("#aadharNoCcrc" + index).val());
		// $("#occupantExtent" + suffix).val($("#occupantExtent_Ccrc" + index).val());
		$("#cultivatorIndexOE").val(index);
		$("#orgextentLabel" + suffix).val(originalExt);


		$("#aadharNo1" + suffix).text($("#aadharNoCcrc" + index).val());
		$("#crSnoLabel" + suffix).text($("#crSno_Ccrc" + index).val());
		$("#khNoLabel" + suffix).text($("#khNo_Ccrc" + index).val());
		$("#ocNameLabel" + suffix).text($("#ocName_Ccrc" + index).val());
		$("#fatherNameLabel" + suffix).text($("#fatherName_Ccrc" + index).val());
		$("#aadharNoLabel" + suffix).text($("#aadharNo_Ccrc" + index).val());

		//		 alert($("#anubhavadarExtent_Ccrc" + index).val());
		//		 alert($("#occupantExtent_Ccrc" + index).val());
		//		alert(avail+'---------------------------'+originalExt)
		if (($("#anubhavadarExtent_Ccrc" + index).val()) >= avail) {
			//			 alert(avail);
			$("#occupantExtent" + suffix).val(avail);
		}
		if (avail > ($("#anubhavadarExtent_Ccrc" + index).val())) {
			//			 alert(originalExt);
			$("#occupantExtent" + suffix).val($("#anubhavadarExtent_Ccrc" + index).val());
		}
		// $("#orgextentLabelOE").val(originalExt);
	}

}


function setModalValues(index, cultivatorType, roleType, originalExt) {
	var suffix = '';
	if (cultivatorType == 'O') {
		suffix = 'OE'
		//		document.getElementById("aadharNo" + suffix).disabled = true;
		var inputValue = $("#aadharNo" + index).val();
		var aadharDisplay = document.querySelector(".aadharDisplay");
		var aadharInput = document.querySelector(".aadharInput");

		//		 if (aadharNo.length > 1) {
		//		        aadharDisplay.style.display = "inline";
		//		        aadharDisplay.textContent = aadharNo;
		//		        aadharInput.style.display = "none";
		//		    } else {
		//		        aadharDisplay.style.display = "none";
		//		        aadharInput.style.display = "inline";
		//		    }

		if (typeof inputValue !== 'undefined' && $("#aadharNo" + index).val() == '') {
			alert('Please Update Pattadar  Aadhaar Number in "Pattadar Empty Aadhaar Updation Screen" in Transactions')
			aadharDisplay.style.display = "inline";
			aadharDisplay.textContent = inputValue;
			aadharInput.style.display = "none";
		}
		if (typeof inputValue !== 'undefined') {
			aadharDisplay.style.display = "inline";
			aadharDisplay.textContent = inputValue;
			aadharInput.style.display = "none";
		}
		//		    else {
		//				
		//		    	 aadharDisplay.style.display = "none";
		//			        aadharInput.style.display = "inline";
		//		    	
		//		        // Display the value in a span
		//		        $("#aadharNo" + index).replaceWith("<span>" + inputValue + "</span>");
		//		    } 
		//		if ($("#aadharNo" + index).val() === '' || $("#aadharNo" + index).val() == null)
		//			document.getElementById("aadharNo" + suffix).disabled = false;
		document.getElementById("occupantExtent" + suffix).disabled = false;
		$("#cultivatorType" + suffix).text($("#cultivatorType" + index).val());
	} else if (cultivatorType == 'L') {
		suffix = 'OE'
		$("#aadharNo" + suffix).val('');
		var inputValue = $("#aadharNo" + index).val();
		var aadharDisplay = document.querySelector(".aadharDisplay");
		var aadharInput = document.querySelector(".aadharInput");
		aadharDisplay.style.display = "none";
		aadharInput.style.display = "inline";
		document.getElementById("aadharNo" + suffix).disabled = false;
		document.getElementById("occupantExtent" + suffix).disabled = false;
		$("#MobileOE").val('');
	$("#MobileOE").text('');
		$("#cultivatorType" + suffix).text($("#cultivatorType" + index).val());
	} else if (cultivatorType == 'K') {
		$("#MobileC").val('');
	$("#MobileC").text('');
		var inputValue = $("#anubhavadarExtent" + index).val();
		// suffix = 'OE'
		$("#orgextentLabelC").val(inputValue);
		$("#availableExtent").val(originalExt);
		$("#owner_tenant").val(cultivatorType);
		$("#refBookingId").val($("#bookingId" + index).val());

	}

	else if (cultivatorType == 'C') {
		$("#MobileOE").val('');
	$("#MobileOE").text('');
		suffix = 'OE'
		$("#owner_tenant").val(cultivatorType);
		document.getElementById("aadharNo" + suffix).disabled = true;
		document.getElementById("occupantExtent" + suffix).disabled = true;
		$("#refBookingId").val($("#bookingId" + index).val());
	}

	$("#crSnoLabel" + suffix).text($("#crSno" + index).val());
	$("#khNoLabel" + suffix).text($("#khNo" + index).val());
	$("#ocNameLabel" + suffix).text($("#ocName" + index).val());
	$("#fatherNameLabel" + suffix).text($("#fatherName" + index).val());
	$("#aadharNoLabel" + suffix).text($("#aadharNo" + index).val());
	$("#cultivatorType" + suffix).val(cultivatorType);
	// if (cultivatorType == 'O') {
	// $("#aadharNo" + suffix).text($("#aadharNo" + index).val());
	// }
	$("#cultivatorModalHeaderId" + suffix).text(roleType);
	$("#cultivatorModalFieldId1" + suffix).text(roleType);

	$("#ccrcModalHeaderId" + suffix).text(roleType);
	$("#ccrcModalFieldId1" + suffix).text(roleType);

	$("#crSno" + suffix).val($("#crSno" + index).val());
	$("#khNo" + suffix).val($("#khNo" + index).val());
	$("#part_key" + suffix).val($("#part_key" + index).val());
	$("#cr_vcode" + suffix).val($("#cr_vcode" + index).val());
	$("#cr_year" + suffix).val($("#cr_year" + index).val());
	$("#cr_season" + suffix).val($("#cr_season" + index).val());
	$("#cultivatorType" + suffix).val(cultivatorType);

	$("#orgextentLabelOE").val(originalExt);
	$("#orgextentLabelC").val($("#anubhavadarExtent" + index).val());

	if (cultivatorType == 'O') {
		$("#MobileOE").val('');
	$("#MobileOE").text('');
		$("#ocName" + suffix).val($("#ocName" + index).val());
		$("#fatherName" + suffix).val($("#fatherName" + index).val());
		$("#aadharNo" + suffix).val($("#aadharNo" + index).val());
		$("#bookingId" + suffix).val($("#bookingId" + index).val());
		$("#cultivatorIndexOE").val(index);
	} else if (cultivatorType == 'L') {
		$("#MobileOE").val('');
	$("#MobileOE").text('');
		$("#ocName" + suffix).val($("#ocName" + index).val());
		$("#fatherName" + suffix).val($("#fatherName" + index).val());
		// $("#aadharNo" + suffix).val($("#aadharNo" + index).val());
		$("#bookingId" + suffix).val($("#bookingId" + index).val());
		$("#cultivatorIndexOE").val(index);
	}
	else if (cultivatorType == 'C') {

		$("#ocName" + suffix).val($("#ocName_Ccrc" + index).val());
		$("#fatherName" + suffix).val($("#fatherName_Ccrc" + index).val());
		$("#aadharNo" + suffix).val($("#aadharNo_Ccrc" + index).val());
		$("#bookingId" + suffix).val($("#bookingId_Ccrc" + index).val());
		$("#occupantExtent" + suffix).val($("#availableExtentC" + index).val());
		$("#cultivatorIndexOE").val(index);

		$("#crSnoLabel" + suffix).text($("#crSno_Ccrc" + index).val());
		$("#khNoLabel" + suffix).text($("#khNo_Ccrc" + index).val());
		$("#ocNameLabel" + suffix).text($("#ocName_Ccrc" + index).val());
		$("#fatherNameLabel" + suffix).text($("#fatherName_Ccrc" + index).val());
		$("#aadharNoLabel" + suffix).text($("#aadharNo_Ccrc" + index).val());
		$("#occupantExtentNoLabel" + suffix).text($("#occupantExtent_Ccrc" + index).val());
	}


	else {
		suffix = 'R'
		$("#crSnoLabel" + suffix).val($("#crSno" + index).val());
		$("#khNoLabel" + suffix).val($("#khNo" + index).val());
		//		$("#orgextentLabel" + suffix).val($("#anubhavadarExtent" + index).val());
		$("#ocNameLabel" + suffix).val($("#ocName" + index).val());
		$("#fatherNameLabel" + suffix).val($("#fatherName" + index).val());
		$("#aadharNo" + suffix).val($("#aadharNo" + index).val());
		$("#aadharNoLabel" + suffix).val($("#aadharNo" + index).val());
		$("#Extent" + suffix).val($("#anubhavadarExtent" + index).val());
		$("#crSno" + suffix).val($("#crSno" + index).val());
		$("#khNo" + suffix).val($("#khNo" + index).val());
		$("#bookingId" + suffix).val($("#bookingId" + index).val());
		$("#part_key" + suffix).val($("#part_key" + index).val());
		$("#cr_vcode" + suffix).val($("#cr_vcode" + index).val());
		$("#cr_year" + suffix).val($("#cr_year" + index).val());
		$("#cr_season" + suffix).val($("#cr_season" + index).val());
		$("#cultivatorType" + suffix).val(cultivatorType);
		$("#roleType" + suffix).val(roleType);

$("#MobileR").val('');
	$("#MobileR").text('');

		$("#crSnoLabel" + suffix).text($("#crSno" + index).val());
		$("#khNoLabel" + suffix).text($("#khNo" + index).val());
		$("#ocNameLabel" + suffix).text($("#ocName" + index).val());
		$("#fatherNameLabel" + suffix).text($("#fatherName" + index).val());
		$("#aadharNoLabel" + suffix).text($("#aadharNo" + index).val());
		$("#aadharNo" + suffix).text($("#aadharNo" + index).val());
		$("#Extent" + suffix).text($("#anubhavadarExtent" + index).val());
		$("#aadharNo2" + index).replaceWith("<span>" + originalExt + "</span>");
	}



	/*else {
		$("#cultivatorIndex").val(index);
	}*/


}

function rofrDetails(sufix) {
	if (validMobilenumber($("#Mobile" + sufix).val())) {
		if ($("#Extent" + sufix).val() > 0) {
			var partkey = $("#part_key" + sufix).val();
			var bookingid = $("#bookingId" + sufix).val();
			var occupextent = $("#Extent" + sufix).val();
			var userid = $("#userid").val();
			var aadhaarNo = $("#aadharNo" + sufix).val();
			var cr_vcode = $("#cr_vcode" + sufix).val();
			var khNo = $("#khNo" + sufix).val();
			var crSno = $("#crSno" + sufix).val();
			var roleType = $("#roleType" + sufix).val();
			var mobile = ($("#Mobile" + sufix).val());
			var cultivatorType = $("#cultivatorType" + sufix).val();
			if (cultivatorType == 'N') {
				cultivatorType = 'O';
			}
			else if (cultivatorType == 'A' || cultivatorType == 'B' || cultivatorType == 'F' || cultivatorType == 'G') {
				cultivatorType = 'L';
			}
			var msg = roleType + ' Data Updated Successfully!';
			var failedMsg = "Failed to Update " + roleType + "  Data";
			var title = "Do you want to add " + roleType;
			//alert(partkey +'  '+ bookingid +'  '+ occupextent +'  '+ userid +'  '+ aadhaarNo +'  '+ cr_vcode +'  '+ khNo +'  '+ crSno)
			Swal.fire({
				title: title,
				showDenyButton: true,
				showCancelButton: false,
				confirmButtonText: "Add",
				denyButtonText: `Cancel`
			}).then((result) => {
				if (result.isConfirmed) {
					$.ajax({
						type: "put",
						url: "./cultivator/owner/update",
						data: {
							"part_key": partkey,
							"bookingId": bookingid,
							"aadharNo": aadhaarNo,
							"occupantExtent": occupextent,
							"updatedby": userid,
							"cultivatorType": cultivatorType,
							"cr_vcode": cr_vcode,
							"khNo": khNo,
							"crSno": crSno,
							"mobile": mobile,
						},
						success: function(data) {
							$('#cultivatorROFRModal').modal('hide');
							var index = $("#cultivatorIndex" + sufix).val();
							$("#searchParam" + index).val('0');
							$("#occupantExtent" + sufix).val('');
							searchData();
							data ?
								alertify.notify(msg, "success", 10) :
								alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
							$('#cultivatorROFRModal').modal('hide');

						},
						error: function(data) {
							$('#cultivatorROFRModal').modal('hide');
							searchData();
							data ?
								alertify.notify(msg, "success", 10) :
								alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
							//				console.log(err);
							//				console.log("Failed to Update Details");
						}
					});
				} else if (result.isDenied) {
					Swal.fire(failedMsg, "", "info");
				}
			});
		}
		else {
			alert('Extent Should be Greater Than Zero')
			return false;
		}
	}
}


function validMobilenumber(a) {
	var mbl_no = a;
	var sub = mbl_no.substring(0, 1);
	if (mbl_no.length === 10 || mbl_no.length === 0) {
		if (mbl_no.length === 10 && (sub === "6" || sub === "7" || sub === "8" || sub === "9")) {
			return true;
		}
		else if (mbl_no.length === 10 && !(sub === "6" || sub === "7" || sub === "8" || sub === "9")) {
			alert("Please enter a valid mobile number");
			return false;
		}
		else if (!mbl_no.length === 10) {
			alert("Please enter a valid mobile number");
			$('#mbl_no' + a).val('');
			return false;
		}
		else if (mbl_no.length === 0) {
			alert("You haven't Updating mobile number");
			return true;
		}

	} else {
		alert("Please enter a valid mobile number");
		$('#mbl_no' + a).val('');
		return false;
	}
	return true;
}

function updateOwnerOrEnjoerDetails(sufix) {
	if (validMobilenumber($("#Mobile" + sufix).val())) {
		if ($("#occupantExtent" + sufix).val() > 0) {
			var cultivatorType = $("#cultivatorTypeOE").val();
			var partkey = $("#part_key" + sufix).val();
			var bookingid = $("#bookingId" + sufix).val();
			var occupextent = $("#occupantExtent" + sufix).val();
			var userid = $("#userid").val();
			var inputVal = ($("#aadharNo2" + sufix).val());
			var mobile = ($("#Mobile" + sufix).val());
			//	var inputgh2 == ($("#aadharNo" + sufix).val());
			if (typeof inputVal !== 'undefined') {
				var aadhaarNo = $("#aadharNo" + sufix).val();
			}
			else {
				var aadhaarNo = $("#aadharNo2" + sufix).val();
			}
			var cr_vcode = $("#cr_vcode" + sufix).val();
			var khNo = $("#khNo" + sufix).val();
			var crSno = $("#crSno" + sufix).val();

			var dupCullti = "C";
			if (cultivatorType != dupCullti) {
				if (cultivatorType == 'L' || cultivatorType == 'O') {
					var aadharNo = $("#aadharNo" + sufix).val();
					if (aadharNo === '' || !/^\d{12}$/.test(aadharNo)) {
						Swal.fire({
							text: "Please enter a valid 12-digit Aadhar number.",
							icon: "error"
						});
						return;
					}
					else {
						if (!validateVerhoeff(aadharNo)) {
							alert('Wrong Aadhar')
							Swal.fire({
								text: "Please enter a valid Aadhar number.",
								icon: "error"
							});
							return;
						}
					}

				}

				if ($("#occupantExtent" + sufix).val() === '') {
					Swal.fire({
						text: "Please fill Occupant Extent.",
						icon: "error"
					});
					return;
				}


				var index = $("#cultivatorIndexOE").val();
				var availableExtent = parseFloat($("#availableExtent" + index).val()).round(5);
				var occupantExtent = parseFloat($("#occupantExtentOE").val()).round(5);
				if (occupantExtent > availableExtent) {
					swal.fire("Entered Occupant Extent is more than available extent. Allowed Extent is  ::  " + availableExtent, "", "warning");
					return false;
				}

				Swal.fire({
					title: "Do you want to update the changes?",
					showDenyButton: true,
					showCancelButton: false,
					confirmButtonText: "Update",
					denyButtonText: `Don't Update`
				}).then((result) => {
					if (result.isConfirmed) {
						$.ajax({
							type: "put",
							url: "./cultivator/owner/update",
							data: {
								"part_key": partkey,
								"bookingId": bookingid,
								"aadharNo": aadhaarNo,
								"occupantExtent": occupextent,
								"updatedby": userid,
								"cultivatorType": cultivatorType,
								"cr_vcode": cr_vcode,
								"khNo": khNo,
								"crSno": crSno,
								"mobile": mobile,
							},
							success: function(data) {
								$('#ownerOrEnjoyerModal').modal('hide');
								var index = $("#cultivatorIndex" + sufix).val();
								$("#searchParam" + index).val('0');
								$("#occupantExtent" + sufix).val('');
								searchData();
								data ?
									alertify.notify("Owner/Enjoyer Data Updated Successfully!", "success", 10) :
									alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
								$('#ownerOrEnjoyerModal').modal('hide');

							},
							error: function(data) {
								$('#ownerOrEnjoyerModal').modal('hide');
								searchData();
								data ?
									alertify.notify("Owner/Enjoyer Data Updated Successfully!", "success", 10) :
									alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
								//						console.log(err);
								//						console.log("Failed to Update Details");
							}
						});
					} else if (result.isDenied) {
						Swal.fire("Failed to Update Owner/Enjoyer Data", "", "info");
					}
				});
			}
		}
		else {
			alert('Extent Should Be Greater Than zero')
			return false;
		}
	}
}

function ccrcDetails(sufix) {
	//alert($("#cr_vcode" + sufix).val())
	if (validMobilenumber($("#Mobile" + sufix).val())) {
		if ($("#occupantExtent" + sufix).val() > 0) {
			var cultivatorType = $("#cultivatorTypeOE").val();
			//	alert( $("#part_key" + sufix).val())
			var dupCullti = "C";
			if (cultivatorType == dupCullti) {
				Swal.fire({
					title: "Do you want to add the CCRC?",
					showDenyButton: true,
					showCancelButton: false,
					confirmButtonText: "Add",
					denyButtonText: `Don't Update`
				}).then((result) => {
					if (result.isConfirmed) {
						$.ajax({
							type: "PUT",
							url: "./cultivator/ccrc/update",
							data: {
								"part_key": $("#part_key" + sufix).val(),
								"bookingId": $("#bookingId" + sufix).val(),
								"aadharNo": $("#aadharNo1" + sufix).val(),
								"occupantExtent": $("#occupantExtent" + sufix).val(),
								"updatedby": $("#userid").val(),
								"cultivatorType": $("#cultivatorType" + sufix).val(),
								"cr_vcode": $("#cr_vcode" + sufix).val(),
								"crSno": $("#crSno" + sufix).val(),
								"khNo": $("#khNo" + sufix).val(),
								"mobile": $("#Mobile" + sufix).val(),
							},
							success: function(resData) {
								var index = $("#cultivatorIndexOE").val();
								$("#searchParam" + index).val('0');
								$("#occupantExtent" + sufix).val('');
								searchData();
								resData ?
									alertify.notify("CCRC Data added Successfully!", "success", 10)
									: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
								$('#ownerOrEnjoyerModal').modal('hide');

							},
							error: function(xhr, err) {
								console.log(err);
								console.log("Failed to Update Details");
							}
						});
					} else if (result.isDenied) {
						Swal.fire("Failed to Update CCRC Data", "", "info");
					}
				});
			}
		}
		}	
		else {
			alert('Extent Should be Greater Than Zero')
			return false;
		}
	}


	var clickCounts = [];

	function editCultivatorDetails(index) {
		var cultivatorType = document.getElementById('cultivatorType' + index).value;
		if (!clickCounts[index]) {
			clickCounts[index] = 0;
		}
		clickCounts[index]++;
		if (clickCounts[index] % 2 === 1 && cultivatorType === 'O') {
			document.getElementById("aadharNo" + index).disabled = true;
			document.getElementById("occupantExtent" + index).disabled = true;
			document.getElementById("ocName" + index).disabled = true;
			document.getElementById("fatherName" + index).disabled = true;
			$("#update" + index).css({ 'display': '' });
			$("#addExtent" ).css({ 'display': '' });
		} else if (clickCounts[index] % 2 === 1 && cultivatorType === 'K') {
			document.getElementById("aadharNo" + index).disabled = false;
			document.getElementById("occupantExtent" + index).disabled = false;
			document.getElementById("ocName" + index).disabled = false;
			document.getElementById("fatherName" + index).disabled = false;
			$("#update" + index).css({ 'display': '' });
		} else if (clickCounts[index] % 2 === 1 && cultivatorType === 'L') {
			document.getElementById("aadharNo" + index).disabled = false;
			document.getElementById("occupantExtent" + index).disabled = false;
			document.getElementById("ocName" + index).disabled = true;
			document.getElementById("fatherName" + index).disabled = true;
			$("#update" + index).css({ 'display': '' });
		} else {
			document.getElementById("aadharNo" + index).disabled = true;
			document.getElementById("occupantExtent" + index).disabled = true;
			document.getElementById("ocName" + index).disabled = true;
			document.getElementById("fatherName" + index).disabled = true;
			$("#update" + index).css({ 'display': 'none' });
			$("#addExtent" ).css({ 'display': 'none' });
		}

		$.ajax({
			type: "GET",
			url: "./cultivator/extent",
			data: {
				"part_key": $("#part_key" + index).val(),
				"khNo": $("#fromKhnoId").val(),
				"cr_vcode": $("#wbvcode").val(),
				"crSno": $("#crSno" + index).val(),
			},
			dataType: 'json',
			success: function(responseJson) {
				console.log(responseJson.anubhavadarExtent);
				var anubhavadarExtent = parseFloat(responseJson.anubhavadarExtent).round(3);
				var totalOccupantExtent = parseFloat(responseJson.occupantExtent).round(3);
				var availableExtent = anubhavadarExtent - totalOccupantExtent;


				$("#availableExtent" + index).val(availableExtent);

				$("#totalOccupantExtent" + index).val(totalOccupantExtent);

				$("#anubhavadarExtent" + index).val(anubhavadarExtent);

				// $('#occupantExtent' + index).attr('title', "Total Extent is :: "
				// + anubhavadarExtent + " and Available Extent is :: " +
				// availableExtent);
				$('#occupantExtent' + index).tooltip();
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Update Details");
			}
		});


	}

	function updateCultivatorDetails(index) {
		var ocName = $("#ocName" + index).val();	
		var fatherName = $("#fatherName" + index).val();		
		var addExtent = $("#addExtent").val();
		
		if ($("#ocName" + index).val() === '') {
			Swal.fire({
				text: "Please fill Pattadhar Name.",
				icon: "error"
			});
			return;
		}

		if ($("#fatherName" + index).val() === '') {
			Swal.fire({
				text: "Please fill Pattadhar Father Name.",
				icon: "error"
			});
			return;
		}

		var aadharNo = $("#aadharNo" + index).val();
		if (aadharNo === '' || !/^\d{12}$/.test(aadharNo)) {
			Swal.fire({
				text: "Please enter a valid 12-digit Aadhar number.",
				icon: "error"
			});
			return;
		}

		var anubhavadarExtent = parseFloat($("#availableExtent" + index).val());
		var occupantExtent = parseFloat($("#occupantExtent" + index).val());
		var newExtent= (parseFloat(occupantExtent) +parseFloat(addExtent)).round(4);
		var existingOccupantExtent = parseFloat($("#existingOccupantExtent" + index).val());
		var totalOccupantExtent = parseFloat($("#totalOccupantExtent" + index).val());
		console.log("anubhavadarExtent:" + anubhavadarExtent);
		console.log("occupantExtent:" + occupantExtent);
		console.log("existingOccupantExtent:" + existingOccupantExtent);
		console.log("totalOccupantExtent_1:" + totalOccupantExtent);
		anubhavadarExtent = anubhavadarExtent;
		var existingAvailableExtent = anubhavadarExtent - totalOccupantExtent;
		totalOccupantExtent = totalOccupantExtent - existingOccupantExtent;
		totalOccupantExtent = totalOccupantExtent + newExtent;
		if (addExtent === '' || addExtent==0 || addExtent == null || isNaN(addExtent) ) {
			Swal.fire({
				text: "Please Add Aditional Extent.",
				icon: "error"
			});
			return false;
		}
		else if (addExtent <0 ) {
			Swal.fire({
				text: "Extent Cannot Be Negative.",
				icon: "error"
			});
			return false;
		}else {

			if (addExtent > anubhavadarExtent.round(3)) {
				swal.fire("Entered Occupant Extent is morethan available extent. Allowed Extent is :: " + anubhavadarExtent.round(3), "", "warning");
				return false;
			}
		}


		var part_key = $("#part_key" + index).val();
		var bookingId = $("#bookingId" + index).val();
		var cr_vcode = $("#cr_vcode" + index).val();
		var khNo = $("#khNo" + index).val();
		var crSno = $("#crSno" + index).val();
		
	
		$.ajax({
			type: "POST",
			url: "./cultivator/owner/details",
			data: {
				"part_key": part_key,
				"bookingId": bookingId,
				"ocName": ocName,
				"fatherName": fatherName,
				"aadharNo": aadharNo,
				"occupantExtent": newExtent,
				"cr_vcode": cr_vcode,
				"khNo": khNo,
				"crSno": crSno,
				"cultivatorType": $("#cultivatorType" + index).val(),
				"updatedby": $("#userid").val(),
			},
			success: function(resData) {
				searchData();
				resData ?
					alertify.notify("Data Updated Successfully!", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Update Details");
			}
		});

	}

	function deleteCultivatorDetails(index) {
		Swal.fire({
			title: "Do you want to delete cultivator data?",
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: "Yes",
			denyButtonText: "No"
		}).then((result) => {
			if (result.isConfirmed) {

				$.ajax({
					type: "DELETE",
					url: "./cultivator/delete",
					data: {
						"part_key": $("#part_key" + index).val(),
						"bookingId": $("#bookingId" + index).val(),
					},
					success: function(data) {
						$("#occupantExtentOE").val('');
						searchData();
						data ?
							alertify.notify("Data Deleted Successfully!", "success", 10)
							: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
					},
					error: function(xhr, err) {
						console.log(err);
						console.log("Failed to Delete Details");
					}
				});
			} else if (result.isDenied) {
			}
		});

	}



	function saveCultivatorData() {
		if (validMobilenumber($("#MobileC").val())) {
			if ($("#occupantExtent").val() > 0) {
				if ($("#ocName").val() === '') {
					Swal.fire({
						text: "Please fill Cultivator Name.",
						icon: "error"
					});
					return;
				}

				if ($("#fatherName").val() === '') {
					Swal.fire({
						text: "Please fill Cultivator Father Name.",
						icon: "error"
					});
					return;
				}


				var aadharNo = $("#aadharNo").val();
				var mobile = ($("#MobileC").val());
				if (aadharNo === '' || !/^\d{12}$/.test(aadharNo)) {
					Swal.fire({
						text: "Please enter a valid 12-digit Aadhar number.",
						icon: "error"
					});
					return;
				}
				else {
					if (!validateVerhoeff(aadharNo)) {
						alert('Wrong Aadhaaar')
						Swal.fire({
							text: "Please enter a valid Aadhar number.",
							icon: "error"
						});
						return;
					}
				}

				if ($("#occupantExtent").val() === '') {
					Swal.fire({
						text: "Please fill Occupant Extent.",
						icon: "error"
					});
					return;
				}

				var index = $("#cultivatorIndex").val();
				var availableExtent = parseFloat($("#availableExtent" + index).val()).round(3);
				var occupantExtent = parseFloat($("#occupantExtent").val()).round(3);

				if (occupantExtent > availableExtent) {
					swal.fire("Entered Occupant Extent is morethan available extent. Allowed Extent is - " + availableExtent, "", "warning");
					return false;
				}

				$.ajax({
					type: "post",
					url: "./cultivator/save",
					data: {
						"khNo": $("#khNo").val(),
						"crSno": $("#crSno").val(),
						"occupname": $("#ocName").val(),
						"occupfname": $("#fatherName").val(),
						"cr_year": $("#cr_year").val(),
						"aadharNo": $("#aadharNo").val(),
						"part_key": $("#part_key").val(),
						"cr_vcode": $("#cr_vcode").val(),
						"crDistCode": $("#wbdcode").val(),
						"crMandCode": $("#wbmcode").val(),
						"cr_season": $("#cr_season").val(),
						"occupantExtent": occupantExtent,
						"refBookingId": $("#refBookingId").val(),
						"cultivatorType": $("#cultivatorType").val(),
						"owner_tenant": $("#owner_tenant").val(),
						"updatedby": $("#userid").val(),
						"cult_updatedby": $("#userid").val(),
						"srno_userid": $("#userid").val(),
						"dcode": $("#dcode").val(),
						"mcode": $("#mcode").val(),
						"mobile": mobile
					},
					success: function(data) {
						$('#ownerOrEnjoyerModal').modal('hide');
						searchData();

						$("#aadharNo").val('');
						$("#ocName").val('');
						$("#fatherName").val('');
						$("#occupantExtent").val('');
						data ?
							alertify.notify("Cultivator Data Saved Successfully", "success", 10)
							: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
						$('#cultivatorModal').modal('hide');
					},
					error: function(xhr, err) {
						console.log(err);
						console.log("Failed to Save Details");
					}
				});
			}
			else {
				alert('Extent Should be Greater Than Zero')
				return false;
			}
		}
	}

	function searchData() {
		$("#contentDivId").html('');
		var kh_no = $("#fromKhnoId").val();
		var vill = $("#village").val();
		if (kh_no === '' && vill === null) {
			alert('Please Select a Village And Enter Khata Number ')
			return false;
		}
		if (vill === '' || vill === null) {
			alert('Please Select a Village')
			return false;
		}
		if (kh_no === '' || kh_no === null) {
			alert('Please Enter Khata Number')
			return false;
		}
		$.ajax({
			type: "GET",
			url: './cultivator/kathaNo/',
			data: {
				"khNo": $("#fromKhnoId").val(),
				"village": $("#village").val(),
			},
			success: function(data) {
				$("#contentDivId").html(data)
			}
		});
	}


	function searchRepCultivatorDtls() {
		var seasonvalueArr = $("#cropYearId").val().split('@');
		$("#contentDivId").html('');
		$.ajax({
			type: "GET",
			url: './repCultivatorDtls/aadharNo/' + $("#aadharNo").val() + '?cropYear=' + seasonvalueArr[1] + '&season=' + seasonvalueArr[0],
			success: function(data) {
				$("#contentDivId").html(data)
			}
		});
	}

	// Change Label Text based on Search Type in Edit Crop Booking details Page
	//---------------------------------
	//Change Label Text based on Search Type in Edit Crop Booking details Page
	function searchValueLabelText() {
		var searchType = $("#searchType").val();

		var searchType = document.getElementById('searchType').value;

		if (searchType === "" || searchType === null) {
			alert('Please select a value')

			return false;
		}
		if (searchType === "1") {
			$('#searchValueLabelId').text('Survey No');
		} else if (searchType === "2") {
			$('#searchValueLabelId').text('Katha No');
		} else if (searchType === "3") {
			$('#searchValueLabelId').text('Aadhar No');
		}
	}

	function searchEditCrBookingDetails() {

		var village = document.getElementById('vcode').value;
		if (village === "" || village === null) {
			alert('Please select village')
			return false;
		}


		var correctionType = document.getElementById('correctionType').value;
		if (correctionType === "" || correctionType === null) {
			alert('Please select a value')
			document.getElementById('correctionType').focus;
			return false;
		}


		var searchValue = document.getElementById('searchValue').value;
		if (searchValue === "" || searchValue === null) {
			alert('Please Enter value')
			document.getElementById('searchValue').focus;
			return false;
		}
		var searchType = $("#searchType").val();
		var aadharNo = '';
		var kathaNo = '';
		var surveyNo = '';
		var cropYearVal = $("#cropYear").val();
		var seasonCropYear = cropYearVal.split('@');


		var searchType = document.getElementById('searchType').value
		if (searchType === "" || searchType === null) {
			alert('Please select a value')
			return false;
		}

		if (searchType == 1) {
			surveyNo = $("#searchValue").val();
		} else if (searchType == 2) {
			kathaNo = $("#searchValue").val();
		} else if (searchType == 3) {
			aadharNo = $("#searchValue").val();
		}
		$("#contentDivId").html('');
		$.ajax({
			type: "GET",
			url: './editCropBookingDtls/details',
			data: {
				"cropYear": seasonCropYear[1],
				"vCode": $("#vcode").val(),
				"correctionType": $("#correctionType").val(),
				"searchType": searchType,
				"kathaNo": kathaNo,
				"surveyNo": surveyNo,
				"aadharNo": aadharNo,
				"season": seasonCropYear[0],
			},
			success: function(data) {
				$("#tableTitleTextId").css({ 'display': '' });
				$("#tableTitleTextId").text($("#correctionType option:selected").text());
				$("#contentDivId").html(data)
			}
		});
	}


	function updateUidDetails(index) {



		var uidNo = $("#newAadharNo" + index).val();

		if (uidNo.length == 12) {

			if (validateVerhoeff(uidNo)) {

			}
			else {
				alert('Enter valid Aadhaar')
				return false;
			}
		}
		else {
			alert('Enter 12 digits')
			return false;
		}


		$.ajax({
			type: "POST",
			url: "./editCropBookingDtls/update",
			data: {
				"bookingid": $("#bookingid" + index).val(),
				"season": $("#season" + index).val(),
				"oc_name": $("#oc_name" + index).val(),
				"oc_fname": $("#oc_fname" + index).val(),
				"occupname": $("#occupname" + index).val(),
				"occupfname": $("#occupfname" + index).val(),
				"cr_sno": $("#cr_sno" + index).val(),
				"kh_no": $("#kh_no" + index).val(),
				"cr_crop": $("#cr_crop" + index).val(),
				"cr_vcode": $("#cr_vcode" + index).val(),
				"variety": $("#variety" + index).val(),
				"cr_farmeruid": $("#cr_farmeruid" + index).val(),
				"newAadharNo": $("#newAadharNo" + index).val(),
				"cr_sow_date": $("#cr_sow_date" + index).val(),
				"cr_no": $("#cr_no" + index).val(),
				"cropyear": $("#cropyear" + index).val(),
				"wbdcode": $("#wbdcode").val(),
				"rec_id": $("#rec_id" + index).val(),
				"correctionType": $("#correctionType").val(),
			},
			success: function(data) {
				searchEditCrBookingDetails();
				data ?
					alertify.notify("Cultivator UID Details Updated Successfully", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Save Details");
			}
		});
	}

	function updateCcrcDetails(index) {


		$.ajax({
			type: "POST",
			url: "./editCropBookingDtls/update",
			data: {
				"bookingid": $("#bookingid" + index).val(),
				"season": $("#season" + index).val(),
				"oc_name": $("#oc_name" + index).val(),
				"oc_fname": $("#oc_fname" + index).val(),
				"occupname": $("#occupname" + index).val(),
				"occupfname": $("#occupfname" + index).val(),
				"newOccupName": $("#newOccupName" + index).val(),
				"newOccupFName": $("#newOccupFName" + index).val(),
				"cr_sno": $("#cr_sno" + index).val(),
				"kh_no": $("#kh_no" + index).val(),
				"cr_crop": $("#cr_crop" + index).val(),
				"cr_vcode": $("#cr_vcode" + index).val(),
				"variety": $("#variety" + index).val(),
				"cr_farmeruid": $("#cr_farmeruid" + index).val(),
				"newAadharNo": $("#newAadharNo" + index).val(),
				"cr_sow_date": $("#cr_sow_date" + index).val(),
				"cr_no": $("#cr_no" + index).val(),
				"cropyear": $("#cropyear" + index).val(),
				"wbdcode": $("#wbdcode").val(),
				"rec_id": $("#rec_id" + index).val(),
				"correctionType": $("#correctionType").val(),
			},
			success: function(data) {
				searchEditCrBookingDetails();
				data ?
					alertify.notify("Cultivator CCRC Details Updated Successfully", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Save Details");
			}
		});
	}


	function updateCultivatorCropDetails(index) {
		$.ajax({
			type: "POST",
			url: "./editCropBookingDtls/update",
			data: {
				"bookingid": $("#bookingid" + index).val(),
				"season": $("#season" + index).val(),
				"cropSeedScheme": $("#cropSeedScheme" + index).val(),
				"waterResId": $("#waterResId" + index).val(),
				"irrmethod": $("#irrmethod" + index).val(),
				"variety": $("#var_old" + index).val(),
				"variety_new": $("#var_new" + index).val(),				
				"cr_sno": $("#cr_sno" + index).val(),
				"kh_no": $("#kh_no" + index).val(),
				"cr_crop": $("#cropname" + index).val(),
				"cr_farmeruid": $("#cr_farmeruid" + index).val(),
				"cr_sow_date": $("#cr_sow_date" + index).val(),
				"cr_no": $("#cr_no" + index).val(),
				"cropyear": $("#cropyear" + index).val(),
				"cr_vcode": $("#cr_vcode" + index).val(),
				"wbdcode": $("#wbdcode").val(),
				"rec_id": $("#rec_id" + index).val(),
				"correctionType": $("#correctionType").val(),
				"userId": $("#userid").val(),
				//			"var_old" : $("#var").val(),
			},
			success: function(data) {
				searchEditCrBookingDetails();
				data ?
					alertify.notify("Cultivator CropDetails Updated Successfully", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Save Details");
			}
		});
	}

	function updateUidCorrectionOfPattaOrEnjoyer(index) {


		var uidNo = $("#newAadharNo" + index).val();


		if (uidNo.length == 12) {

			if (validateVerhoeff(uidNo)) {

			}
			else {
				alert('Enter valid Aadhaar')
				return false;
			}
		}
		else {
			alert('Enter 12 digits')
			return false;
		}

		$.ajax({
			type: "POST",
			url: "./editCropBookingDtls/update",
			data: {
				"bookingid": $("#bookingid" + index).val(),
				"season": $("#season" + index).val(),
				"oc_name": $("#oc_name" + index).val(),
				"oc_fname": $("#oc_fname" + index).val(),
				"occupname": $("#occupname" + index).val(),
				"occupfname": $("#occupfname" + index).val(),
				"cr_sno": $("#cr_sno" + index).val(),
				"kh_no": $("#kh_no" + index).val(),
				"cr_crop": $("#cr_crop" + index).val(),
				"cr_vcode": $("#cr_vcode" + index).val(),
				"variety": $("#variety" + index).val(),
				"cr_farmeruid": $("#cr_farmeruid" + index).val(),
				"newAadharNo": $("#newAadharNo" + index).val(),
				"cr_sow_date": $("#cr_sow_date" + index).val(),
				"cr_no": $("#cr_no" + index).val(),
				"cropyear": $("#cropyear" + index).val(),
				"wbdcode": $("#wbdcode").val(),
				"rec_id": $("#rec_id" + index).val(),
				"correctionType": $("#correctionType").val(),
			},
			success: function(data) {
				searchEditCrBookingDetails();
				data ?
					alertify.notify("Correction Of Pattadhar / Enjoyer Details Updated Successfully", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Save Details");
			}
		});
	}

	function updateOthersDetails(index) {

		var uidNo = $("#newAadharNo" + index).val();

		if (uidNo.length == 12) {

			if (validateVerhoeff(uidNo)) {

			}
			else {
				alert('Enter valid Aadhaar')
				return false;
			}
		}
		else {
			alert('Enter 12 digits')
			return false;
		}
		$.ajax({
			type: "POST",
			url: "./editCropBookingDtls/update",
			data: {

				"bookingid": $("#bookingid" + index).val(),
				"season": $("#season" + index).val(),
				"oc_name": $("#oc_name" + index).val(),
				"oc_fname": $("#oc_fname" + index).val(),
				"occupname": $("#occupname" + index).val(),
				"occupfname": $("#occupfname" + index).val(),
				"newOccupName": $("#newOccupName" + index).val(),
				"newOccupFName": $("#newOccupFName" + index).val(),
				"cr_sno": $("#cr_sno" + index).val(),
				"kh_no": $("#kh_no" + index).val(),
				"cr_crop": $("#cr_crop" + index).val(),
				"cr_vcode": $("#cr_vcode" + index).val(),
				"variety": $("#variety" + index).val(),
				"cr_farmeruid": $("#cr_farmeruid" + index).val(),
				"newAadharNo": $("#newAadharNo" + index).val(),
				"cr_sow_date": $("#cr_sow_date" + index).val(),
				"cr_no": $("#cr_no" + index).val(),
				"cropyear": $("#cropyear" + index).val(),
				"wbdcode": $("#wbdcode").val(),
				"rec_id": $("#rec_id" + index).val(),
				"correctionType": $("#correctionType").val(),
			},
			success: function(data) {
				searchEditCrBookingDetails();
				data ?
					alertify.notify("updateOthersDetails Updated Successfully", "success", 10)
					: alertify.notify("Something went Wrong. Please Try again after some time or Please Contact Support Team", "warning", 10);
			},
			error: function(xhr, err) {
				console.log(err);
				console.log("Failed to Save Details");
			}
		});
	}

	//----------------------------------------



	function mod(num, divisor) {
		//alert('in mod');
		return ((num % divisor) + divisor) % divisor;
	}

	function validateVerhoeff(num) {
		var d = [
			[0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
			[1, 2, 3, 4, 0, 6, 7, 8, 9, 5],
			[2, 3, 4, 0, 1, 7, 8, 9, 5, 6],
			[3, 4, 0, 1, 2, 8, 9, 5, 6, 7],
			[4, 0, 1, 2, 3, 9, 5, 6, 7, 8],
			[5, 9, 8, 7, 6, 0, 4, 3, 2, 1],
			[6, 5, 9, 8, 7, 1, 0, 4, 3, 2],
			[7, 6, 5, 9, 8, 2, 1, 0, 4, 3],
			[8, 7, 6, 5, 9, 3, 2, 1, 0, 4],
			[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
		];

		// permutation table p
		var p = [
			[0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
			[1, 5, 7, 6, 2, 8, 3, 0, 9, 4],
			[5, 8, 0, 3, 7, 9, 6, 1, 4, 2],
			[8, 9, 1, 6, 0, 4, 3, 5, 2, 7],
			[9, 4, 5, 3, 1, 2, 6, 8, 7, 0],
			[4, 2, 8, 6, 5, 7, 3, 9, 0, 1],
			[2, 7, 9, 3, 8, 0, 6, 4, 1, 5],
			[7, 0, 4, 6, 9, 1, 3, 2, 5, 8]
		];

		var c = 0;
		var myArray = [];
		myArray = StringToReversedIntArray(num);
		// alert(myArray);
		for (var i = 0; i < myArray.length; i++) {
			c = d[c][p[mod(i, 8)][myArray[i]]];
			//	alert(c);
		}
		return (c == 0);
	}
	function StringToReversedIntArray(num) {
		var myArray = [];

		for (var i = 0; i < num.length; i++) {
			myArray[i] = parseInt(num.substring(i, i + 1));
		}
		myArray = Reverse(myArray);

		return myArray;

	}
	function Reverse(myArray) {
		var reversed = [];

		for (var i = 0; i < myArray.length; i++) {
			reversed[i] = myArray[myArray.length - (i + 1)];
		}
		return reversed;
	}


	function Validate(e) {
		var key = e.keyCode;
		return (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || key == 8 || key == 32 || key == 46; // Allow A-Z, a-z, backspace, space, and period
	}
	function ValidateNum(e) {
		var key = e.keyCode;
		return (key >= 48 && key <= 57) || key == 8;
	}



