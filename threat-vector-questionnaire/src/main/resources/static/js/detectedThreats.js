//
//  Threat Vector Questionnaire
//
//  Copyright (C) 2018 Applied Visions - http://securedecisions.com
//
//  Written by AITEK Security - http://aiteksecurity.com
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
var activeFilter = 4;

var filterList = Array();
filterList[0] = "open";
filterList[1] = "pending";
filterList[2] = "review";
filterList[3] = "mitigated";
filterList[4] = "none";

var imageList = Array();
imageList[0] = "/images/newFlag.png";
imageList[1] = "/images/pendingFlag.png";
imageList[2] = "/images/reviewFlag.png";
imageList[3] = "/images/mitigatedFlag.png";

var statusList=Array();

statusList[0]="open";
statusList[1]="pending";
statusList[2]="review";
statusList[3]="mitigated";

function switchImage(obj){
	var image = obj.parentNode.childNodes[1];
	var imageIndex = obj.options[obj.selectedIndex].value;
    image.setAttribute("src",imageList[imageIndex]);
    filter(activeFilter,activeFilter)
    obj.parentNode.parentNode.childNodes[11].childNodes[0].checked = true;
}

function switchImageOnLoad(){
	
	setFilterColor(4);
	
	var table = document.getElementById("detectedThreatsTable");
	
	for (var i = 0, row; row = table.rows[i]; i++) {
		if(row.id != 'fillerRow'){
			var image = row.cells[1].firstElementChild
			var threatStatus = row.cells[1].childNodes[3].selectedIndex;
			
			switch(threatStatus){
			case 0:
				image.setAttribute("src",imageList[0]);
				break;
			case 1:
				image.setAttribute("src",imageList[1]);
				break;
			case 2:
				image.setAttribute("src",imageList[2]);
				break;
			case 3:
				image.setAttribute("src",imageList[3]);
				break;
			}
		}
	}
}

function performBulkAction(){	
	var dropdown = document.getElementById("bulkActionDropdown");
	var actionIndex = dropdown.options[dropdown.selectedIndex].text;
	var table = document.getElementById("detectedThreatsTable");
	
	for (var i = 0, row; row = table.rows[i]; i++) {
		if(row.id != 'fillerRow' && row.style.display != "none"){
			var checkboxStatus = row.firstElementChild.firstElementChild.checked;
			var threatStatus = row.cells[1].childNodes[3];
			
			if(checkboxStatus == true){
				performAction(actionIndex, threatStatus);
				switchImage(threatStatus);
			}
		}
	}
	
	filter(activeFilter,activeFilter);
}

function performAction(actionIndex, threatStatus){
	switch(actionIndex){
	
	case "OPEN":
		threatStatus.selectedIndex = 0;
		break;
	case "PENDING":
		threatStatus.selectedIndex = 1;
		break;
	case "REVIEW":
		threatStatus.selectedIndex = 2;
		break;
	case "MITIGATED":
		threatStatus.selectedIndex = 3;
		break;
	}
}

function filter(filterID) {
	var filter = filterList[filterID].toUpperCase();
	var table = document.getElementById("detectedThreatsTable");

	for (var i = 0, row; row = table.rows[i]; i++) {
		if(row.id != 'fillerRow'){
			if(filter=="NONE"){
				removeFilter()
			}else{
				var threatStatus = statusList[row.cells[1].childNodes[3].value];
				if(threatStatus.toUpperCase() == filter){
					row.style.display = "";
				}else{
					row.style.display = "none";
				}
			}
		}
	}
	
	clearFilterColor();
	setFilterColor(filterID);
}


function setFilterColor(newActiveButton){
	var newButton = document.getElementsByName(filterList[newActiveButton]+"Button")[0];
	newButton.style.backgroundColor = "#cccccc";
	activeFilter=newActiveButton;
}

function clearFilterColor(){
	var button = document.getElementsByName(filterList[activeFilter]+"Button")[0];
	button.style.backgroundColor = "#ffffff";
}

function removeFilter(){
	var table = document.getElementById("detectedThreatsTable");
	for (var i = 0, row; row = table.rows[i]; i++) {
		row.style.display = "";
	}
	clearFilterColor();
	setFilterColor(0);
}

function selectAll(source) {
	checkboxes = document.getElementsByName('threatListItem');
	for(var i=0, n=checkboxes.length;i<n;i++) {
		checkboxes[i].checked = source.checked;
	}
}

function deSelectAll() {
	checkboxes = document.getElementsByName('threatListItem');
	for(var i=0, n=checkboxes.length;i<n;i++) {
		checkboxes[i].checked = false;
	}
}

function openThreatDetails(obj) {
	var resultID = obj.value;
	window.open("/scan/details/"+resultID, 'window', 'width=900,height=800');
}

function saveChanges(scanId){
	var table = document.getElementById("detectedThreatsTable");
		for (var i = 0, row; row = table.rows[i]; i++) {
			var rowId = row.id;
			if(rowId != 'fillerRow'){
				if(row.childNodes[11].childNodes[0].checked){
					var jsonObject= new Object();
					jsonObject.resultId = rowId;
					jsonObject.status = row.cells[1].childNodes[3].selectedOptions[0].innerText;
					
					$.ajax({
					      type: "POST",
					      contentType : 'application/json; charset=utf-8',
					      dataType : 'json',
					      url: "/scan/"+scanId+"/updateResults",
					      data: JSON.stringify(jsonObject),
					      success :function(result) {
					    	  location.reload();
					     }
				})
			}
		}
	}
	alert('Changes Saved Successfully')
}

