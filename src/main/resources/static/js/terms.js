function addBar(){
    var adMark='<input type="submit" value="Download" onclick="addModel();"/>';
    var chMark='<input type="checkbox" id="termCond" value="" onclick="addBar();"/>I understand and agree to terms and conditions listed above.<br/>';
    if(document.getElementById("termCond").checked){
        document.getElementById("terMark").innerHTML=chMark+adMark;
        document.getElementById("termCond").checked=true;
    }else{
        document.getElementById("terMark").innerHTML=chMark;
        document.getElementById("termCond").checked=false;
    }
}