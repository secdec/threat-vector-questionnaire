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
var imageList = Array();
imageList[0] = "/images/newFlag.png";
imageList[1] = "/images/pendingFlag.png";
imageList[2] = "/images/reviewFlag.png";
imageList[3] = "/images/mitigatedFlag.png";

function switchImageOnLoad(){
	var imageStatus = document.getElementById("threatStatus").innerText;
	var image = document.getElementsByTagName("img")[0];
	switch(imageStatus){
	case "OPEN":
		image.setAttribute("src",imageList[0]);
		break;
	case "PENDING":
		image.setAttribute("src",imageList[1]);
		break;
	case "REVIEW":
		image.setAttribute("src",imageList[2]);
		break;
	case "MITIGATED":
		image.setAttribute("src",imageList[3]);
		break;
	}
}