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
$(window).load(function () {
	
	$('.list-group-item').show();

	$('.questionFilter').click(function () {
				
		clearActiveButton();

		$(this).addClass('activeButton');
		
		$('.list-group-item').hide();
		
		var filterValue = $(this).val();
		
		filterThreats(filterValue);
	});
	
	$('.showAllFilter').click(function () {
			
		clearActiveButton();

		$(this).addClass('activeButton');
		
		$('.list-group-item').show();
		
	});
	
	function filterThreats(filterValue){
			
		
		
		var ul = document.getElementById("fileList");
		var li = ul.getElementsByClassName('fileItem');
		
		for (x = 0; x < li.length; x++) {
	        var secCaps = li[x].getElementsByClassName('secCapItem');
	        if(secCaps.length > 0 ){
	        	var display = false;
		        for (y = 0; y < secCaps.length; y++) {
		        	var secCap = secCaps[y].innerText; 	
		        	if(secCap.replace(/\s/g,'').toLowerCase() == filterValue.toLowerCase()){
		                display = true;
		                break;
		            }	
		        }
		        if(!display){
	        		li[x].style.display = "none";
	        	}else{
	        		li[x].style.display = "";
	        	}
	        }else{
	        	li[x].style.display = "none";
	        }
	    }
	}
	
	
	function clearActiveButton(){
		var filterList = document.getElementsByName('questionFilter');
		
		for (i = 0; i < filterList.length; i++) {	
			filterList[i].classList.remove('activeButton');
		};
	}	
	
});