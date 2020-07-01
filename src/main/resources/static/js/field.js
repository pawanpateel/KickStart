function crTextBox(mane, di, zise ){
    var prepareOut='<input type="text" name="'+mane+'" id="'+ di+'" '+
    'value=""  onmouseout="makeFirstChar(\''+di+'\')"';
    if(zise!=null){
        prepareOut=prepareOut+' size="'+zise+'"';
        prepareOut=prepareOut+' pattern="[0-9.]+" maxlength="30" title="It should have only numbers or decimal point."';
    }else{
    	prepareOut=prepareOut+' pattern="[A-Za-z0-9_]+" maxlength="30" title="Field name should have only alphabets, numbers or underscore."';
    }
    prepareOut=prepareOut+'/>';
    return    prepareOut;
}
function crChkBox(mane,di){
    return '<input type="checkbox" name="'+mane+'"  id="'+di+'" value="true"/>';
}
function makeFirstChar(inString){
	var fullStr=document.getElementById(inString).value;
	if(fullStr!=null && fullStr!=undefined && fullStr!=''){
	var firstChar=document.getElementById(inString).value[0].toLowerCase();
	document.getElementById(inString).value=firstChar+fullStr.substr(1,fullStr.length-1);
	}
}
function addField(inputModel) {
    
    var nameArray=[];
    var typeArray=[];
    var idArray=[];
    var nulArray=[];
    var emptyArray=[];
    var minArray=[];
    var maxArray=[];
    var relArray=[];
    var i=0;
    var partId='models'+inputModel+'.fields';
    var partName='models['+inputModel+'].fields[';
    while(document.getElementById(partId+i+".fieldName")!=null){
        nameArray[i]=document.getElementById(partId+i+'.fieldName').value;
        typeArray[i]=document.getElementById(partId+i+'.dataType').value;
        idArray[i]=document.getElementById(partId+i+'.isPrimary1').checked;
        nulArray[i]=document.getElementById(partId+i+'.isNullable1').checked;
        emptyArray[i]=document.getElementById(partId+i+'.canBeEmpty1').checked;
        minArray[i]=document.getElementById(partId+i+'.minValue').value;
        maxArray[i]=document.getElementById(partId+i+'.maxValue').value;
        relArray[i]=document.getElementById(partId+i+'.relationType').value;
        i++;
  }
    var tempList=document.getElementById("mdoleList").value;
    var arrayModels=tempList.split(",");   
    var addSelect="";
    for(let k=0; k<arrayModels.length;k++){
    	let nameModel=arrayModels[k].trim();
    	if(k!=inputModel){
    		addSelect=addSelect+'<option value="'+nameModel+'">'+nameModel+'</option>'
    	}
    }
  var typeDrop=
	 ' <select name="'+partName+i+'].dataType" id="'+partId+i+'.dataType" onchange="hidConstraint('+inputModel+','+i+')">'+
        '<option value="int" selected>int</option>'+
        '<option value="long">long</option>'+
        '<option value="float">float</option>'+
        '<option value="double">double</option>'+
        '<option value="String">String</option>'+
        '<option value="Date">Date</option>'+        
        '<option value="boolean">boolean</option>'+        
        '<option value="BigDecimal">BigDecimal</option>'+
        '<option value="BigInteger">BigInteger</option>'+
        '<option value="byte">byte</option>'+
        '<option value="short">short</option>'+
        '<option value="char">char</option>'+
        addSelect+
    '</select>';
  var relDrop=
		 ' <select name="'+partName+i+'].relationType" id="'+partId+i+'.relationType" onchange="reflex('+inputModel+', '+i+')" disabled="true">'+
	        '<option value="onetoone" selected>onetoone</option>'+
	        '<option value="onetomany">onetomany</option>'+
	        '<option value="manytoone">manytoone</option>'+
	        '<option value="manytomany">manytomany</option>'+
	    '</select>';
  document.getElementById(inputModel+"ListField").innerHTML=
    document.getElementById(inputModel+"ListField").innerHTML+
    '<div id="'+inputModel+'Group'+i+'">'+
    typeDrop+
    crTextBox(partName+i+'].fieldName', partId+i+'.fieldName', null)+
    relDrop+
   crChkBox(partName+i+'].isPrimary', partId+i+'.isPrimary1')+'ID'+
   crChkBox(partName+i+'].isNullable', partId+i+'.isNullable1')+'Nullable'+
   crChkBox(partName+i+'].canBeEmpty', partId+i+'.canBeEmpty1')+'Can Be Empty'+
    crTextBox(partName+i+'].minValue',partId+i+'.minValue',1)+'Min'+
    crTextBox(partName+i+'].maxValue',partId+i+'.maxValue',1)+'Max'+
    '<div class="buttonStyle" id="'+inputModel+'delField'+i+'" onclick="dlte('+inputModel+','+i+')">Delete</div></div><br/>';    
    
    document.getElementById(inputModel+"ShowHide").innerHTML="Hide Fields";
i=0;
  while(document.getElementById(partId+i+'.fieldName')!=null){
    document.getElementById(partId+i+'.fieldName').value=nameArray[i];
    document.getElementById(partId+i+'.dataType').value=typeArray[i];
    document.getElementById(partId+i+'.isPrimary1').checked=idArray[i];
    document.getElementById(partId+i+'.isNullable1').checked=nulArray[i];
    document.getElementById(partId+i+'.canBeEmpty1').checked=emptyArray[i];
    document.getElementById(partId+i+'.minValue').value=minArray[i];
    document.getElementById(partId+i+'.maxValue').value=maxArray[i];
    document.getElementById(partId+i+'.relationType').value=relArray[i];
    i++;
    }
  i--;
  document.getElementById(partId+i+'.fieldName').value="";
  document.getElementById(partId+i+'.dataType').value="int";
  document.getElementById(partId+i+'.isPrimary1').checked=false;
  document.getElementById(partId+i+'.isNullable1').checked=false;
  document.getElementById(partId+i+'.canBeEmpty1').checked=false;
  document.getElementById(partId+i+'.minValue').value="";
  document.getElementById(partId+i+'.maxValue').value="";
  document.getElementById(partId+i+'.relationType').value="onetoone";
}
function hideModel(inputModel) {
    var showOrHide=document.getElementById(inputModel).innerHTML;
    var listField=inputModel.replace("ShowHide","ListField");
    if (showOrHide==="Hide Fields"){
        document.getElementById(listField).style.display="none";
        document.getElementById(inputModel).innerHTML="Show Fields";
    }else{
        document.getElementById(listField).style.display="block";
        document.getElementById(inputModel).innerHTML="Hide Fields"; 
    }
}

function dlte(inputModel, frmIndex ) {
	var partId='models'+inputModel+'.fields';
	document.getElementById(partId+frmIndex+'.fieldName').value="tnkvaiaigkddiahul";  
	document.getElementById(inputModel+'Group'+frmIndex).style.display="none";
	  
}

function hideRel(inputModel, frmIndex ) {
	document.getElementById(inputModel+'Group'+frmIndex).style.display="none";
	  
}

function hidConstraint(inputModel, frmIndex ) {
	var partId='models'+inputModel+'.fields';
    var frmType=document.getElementById(partId+frmIndex+'.dataType').value;
    if(frmType =="int" ||
    frmType =="long" ||
    frmType =="float" ||
    frmType =="double" ||
    frmType =="BigDecimal" ||
    frmType =="BigInteger" ||
    frmType =="byte" ||
    frmType =="short"){
    	document.getElementById(partId+frmIndex+'.isPrimary1').disabled=false;
    	document.getElementById(partId+frmIndex+'.isNullable1').disabled=false;
    	document.getElementById(partId+frmIndex+'.canBeEmpty1').disabled=false;
    	document.getElementById(partId+frmIndex+'.minValue').disabled=false;
    	document.getElementById(partId+frmIndex+'.maxValue').disabled=false;
    	document.getElementById(partId+frmIndex+'.relationType').disabled=true;    	
    }else if(frmType =="boolean" ||
    	    frmType =="String" ||
    	    frmType =="char"){
    	document.getElementById(partId+frmIndex+'.isPrimary1').disabled=false;
    	document.getElementById(partId+frmIndex+'.isNullable1').disabled=false;
    	document.getElementById(partId+frmIndex+'.canBeEmpty1').disabled=false;
    	document.getElementById(partId+frmIndex+'.minValue').value="";
    	document.getElementById(partId+frmIndex+'.maxValue').value="";
    	document.getElementById(partId+frmIndex+'.minValue').disabled=true;
    	document.getElementById(partId+frmIndex+'.maxValue').disabled=true;
    	document.getElementById(partId+frmIndex+'.relationType').disabled=true;    	
}else{
    	document.getElementById(partId+frmIndex+'.isPrimary1').checked=false;
    	document.getElementById(partId+frmIndex+'.isNullable1').checked=false;
    	document.getElementById(partId+frmIndex+'.canBeEmpty1').checked=false;
    	document.getElementById(partId+frmIndex+'.minValue').value="";
    	document.getElementById(partId+frmIndex+'.maxValue').value="";
    	document.getElementById(partId+frmIndex+'.isPrimary1').disabled=true;
    	document.getElementById(partId+frmIndex+'.isNullable1').disabled=true;
    	document.getElementById(partId+frmIndex+'.canBeEmpty1').disabled=true;
    	document.getElementById(partId+frmIndex+'.minValue').disabled=true;
    	document.getElementById(partId+frmIndex+'.maxValue').disabled=true;
    	if(frmType=="Date"){
    		document.getElementById(partId+frmIndex+'.relationType').disabled=true;
    	}else{
    		document.getElementById(partId+frmIndex+'.relationType').disabled=false;
    	}
    }
   
}

function reflex(inputModel, frmIndex ) {
	var partId='models'+inputModel+'.fields';	
	var frmModel=document.getElementById('mdlName'+inputModel).innerHTML;	
	frmModel=frmModel.replace("Add Fields For ","");
    var toModel=document.getElementById(partId+frmIndex+'.dataType').value;
    var frmRelType=document.getElementById(partId+frmIndex+'.relationType').value;
    var toRelType;
    if(frmRelType=='onetomany'){
    	toRelType="manytoone";
    }else if(frmRelType=='manytoone'){
    	toRelType="onetomany";
    }else if(frmRelType=='manytomany'){
    	toRelType="manytomany";
    }else if(frmRelType=='onetoone'){
    	toRelType="onetoone";
    }
    
    var k=0;
    var tempStore="";
    while(document.getElementById('mdlName'+k)!=null){
    	tempStore=document.getElementById('mdlName'+k).innerHTML;
    	tempStore=tempStore.replace("Add Fields For ","");
    	if(tempStore.includes(toModel)){
    		break;
    	}
    	k++;
    }
    var toPartId='models'+k+'.fields';
    var toType;
    var addNew=true;
    var i=0;
    while(document.getElementById(toPartId+i+'.relationType')!=null){
    
        toType=document.getElementById(toPartId+i+'.dataType').value;
        if(toType==frmModel){        	
            document.getElementById(toPartId+i+'.relationType').value=toRelType;   
            addNew=false;
            if(toRelType=='manytoone' || toRelType=='manytomany'){
        		document.getElementById(toPartId+i+'.fieldName').value="fld"+	frmModel;
        		hideRel(k,i);
        		addField(k);
        	}else if(toRelType=='onetoone'){
        		document.getElementById(toPartId+i+'.dataType').value="int";
        		dlte(k, i);
        	}
        	break;
        }
        i++;
  }
    if(addNew){
    	addField(k);
    	document.getElementById(toPartId+i+'.dataType').value=frmModel;  
    	document.getElementById(toPartId+i+'.relationType').value=toRelType;
    	if(toRelType=='manytoone' || toRelType=='manytomany'){
    		document.getElementById(toPartId+i+'.fieldName').value="fld"+	frmModel;
    	}
    	hidConstraint(k, i );
hideRel(k,i);
    }
    
}