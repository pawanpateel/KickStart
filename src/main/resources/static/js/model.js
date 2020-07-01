function makeFirstChar(inString){	
	var fullStr=document.getElementById(inString).value;
	var firstChar=document.getElementById(inString).value[0].toUpperCase();
	document.getElementById(inString).value=firstChar+fullStr.substr(1,fullStr.length-1);
}
function dlte(indax){
	document.getElementById("models"+indax+".modelName").value="tnkvaiaigkddiahul";
	document.getElementById("models"+indax+".modelName").style.display="none";
	document.getElementById("delModel"+indax).style.display="none";
}
function addModel() {
    var valueArray=[];
    var i=0;
    while(document.getElementById("models"+i+".modelName")!=null){
        valueArray[i]=document.getElementById("models"+i+".modelName").value;
        i++;
    }
    
  document.getElementById("listModel").innerHTML=document.getElementById("listModel").innerHTML+
  '<input type="text" name="models['+i+'].modelName" id="models'+i+'.modelName" value="" '+
  'maxlength="30" pattern="[A-Za-z_]+" title="Model name should have only alphabets or underscore." onmouseout="makeFirstChar(\'models'+i+'.modelName\')"/><div id="delModel'+i+'" class="buttonStyle" onclick="dlte('+i+')">Delete</div><br/>';
  i=0;
  while(document.getElementById("models"+i+".modelName")!=null){
        document.getElementById("models"+i+".modelName").value=valueArray[i];
        i++;
  }
  i--;
  document.getElementById("models"+i+".modelName").value="";
}