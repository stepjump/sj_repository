<?
/*

윈도우 APM 서버 전용 웹 파일매니저

설치 방법 : 계정의 최상위 폴더에 업로드하신 뒤 비밀번호만 바꿔주시면 됩니다.
※ 참고 ※ 현재 보시는 FTP파일과, 아이콘 폴더는 리스트에 나타나지 않습니다.
개인용으로만 사용하시는게 좋을겁니다.

▶ 함부로 수정해서 배포하지는 말아주세요.
▶ 원본을 출처 밝히고 배포하는것은 허락합니다.
▶ 이 주석은 삭제 금지ㅠ_ㅜ;

이메일 : punchto@hotmail.com
홈페이지 : http://punchto.dothome.co.kr

*/

$admin_pass = "gu7312!!"; //비밀번호 수정해주세요.
$max_filesize = 10*1024*1024; //한번에 업로드 가능한 최대 파일용량. byte 단위. 현재 10MB
$icon_url= "ftp_icon"; //아이콘이 들어있는 폴더
$ftp_file= "ftp.php"; //현재 보고있는 파일이름.
$textarea_size = "75%"; //글쓰기 테이블의 가로사이즈. 넓을수록 소스 편집이 편해지겠죠?
$textarea_rows = "30"; //글쓰기 폼의 줄수.

// 여기서부터는 그냥 놔두시는게 좋습니다.
$pwd=chop(realpath("./"));
$root= explode("\\", $pwd );
$root = $root[sizeof($root)-1];
$default_dir = "."; 

if(!$dir){ $dir = $default_dir;} 
// 남은 용량 계산
$remain_size= diskfreespace("./");
if( $remain_size >= (1024*1024)){$remain_size = number_format(disk_free_space("./")/1024/1024)."MB";}
elseif( $remain_size < (1024*1024)){$remain_size = number_format(disk_free_space("./")/1024)."KB";}
// 여기까지 용량계산

function check_name($a, $b){      // 파일, 폴더 생성시 이름에 특수문자 입력 금지
	$nostr=explode(" ","\{ \} \: \; \\\" \' \, \/ \? \! \@ \\$ \# \^ \& \* \; \\\\");
	while($check=each($nostr)){
		if(eregi($check[1], $a)){
			echo"<script>alert (\"".$b."이름에 $check[1] 등이 들어갈 수는 없습니다\"); history.go(-1);</script>";
			exit;
		}
		if(!$a){
			echo"<script>alert(\"생성할 ".$b."의 이름을 입력해주세요\"); history.go(-1);</script>";
			exit;
			}
	}
}
function check_make($msg){
		global $filename;
		global $content;
		global $dir;
		global $ftp_file;
		echo"<center><br>$dir/$filename <br>$msg<br>";
		echo"<form method=post action=$ftp_file>
		<input type=hidden name=mode value=make>
		<input type=hidden name=filename value=\"$filename\">
		<input type=hidden name=content value=\"$content\">
		<input type=hidden name=dir value=\"$dir\">
		<input type=submit value=예 style=width:60><input type=button value=아니오 onclick=\"javascript:history.go(-1);\" style=width:60>
		</form>";
}
// 여기부터 로긴 페이지
if ($action=="login_ok"){ 
	if ($password==$admin_pass){
		SETCOOKIE(logid, crypt($admin_pass));
		echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
		exit;
	}
	else{
		echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
		exit;
	}
}
elseif ($action=="logout"){
	SETCOOKIE(logid, "");
	echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
	exit;
}
elseif ($logid!=crypt($admin_pass, $logid)){	
	echo"<br><br><center><form action=$ftp_file method=post>비밀번호 <input type=hidden name=action value=login_ok><input style=font-size:12px;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;height:18;margin:1; type=password name=password><input style=font-size:12px;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;height:18;margin:1; type=submit value=로그인><br><br>"; 
	exit;
}
// 여기까지 로그인

//상위디렉토리로 접근금지 소스
	$realpath=chop(realpath($dir));
	if (!eregi($root, $realpath)){ 
		echo"<script>alert(\"경고!! 잘못된 접근입니다.\"); history.go(-1);</script>";
		}
	if($pwd == $realpath){
		$dir = $default_dir;
	}
//여기까지 상위디렉토리 접근금지 소스

//여기부터 메뉴
	echo "<style>
			.name A:link          {color:black; TEXT-decoration: none} 
			.name A:visited       {color:black; TEXT-decoration: none} 
			.name A:active        {color:black; TEXT-decoration: none} 
			.name A:hover         {color:#ff9900; TEXT-decoration: none}

			A:link          {color:blue; TEXT-decoration: none} 
			A:visited       {color:blue; TEXT-decoration: none} 
			A:active        {color:blue; TEXT-decoration: none} 
			A:hover         {color:gray; TEXT-decoration: none}

			submit,input {font-size:8pt;font-family:tahoma;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;margin:1;height=18}
			textarea {font-size:9pt;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;margin:1;background-image:url('$icon_url/textarea_bg.gif');}

			body,table {font-size:8pt;font-family:tahoma;}

			</style> 
		<br><center><a href=$ftp_file>처음화면</a> | <a href=\"$ftp_file?action=fmake&dir=$dir\">새파일작성</a> | <a href=\"$ftp_file?action=dmake&dir=$dir\">새폴더작성</a> | <a href=\"$ftp_file?action=upload&dir=$dir\">파일업로드</a> | <a href=$ftp_file?action=logout>로그아웃</a><br><br>";

//여기까지 메뉴

// 새 파일 생성 페이지
if ($action=="fmake"){
	echo "
		<br><form method=post action=$ftp_file><input type=hidden name=dir value=\"$dir\"><input name=action type=hidden value=fmake_check>\n
		<table border=0 align=center width=$textarea_size><tr><td width=60 align=right>파일이름 : </td><td><input type=text name=filename style=width:100% value=\"$filename\"></td></tr>
		<tr><td width=60 align=right valign=top>내 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용 : </td><td><textarea name=content style=width:100%; rows=$textarea_rows>";
	if($mode=="modify"){
			$fp = fopen($dir."/".$filename, r);
			while($content =fgets($fp)){
			echo htmlspecialchars($content);
			}
			fclose($fp);
	}
	echo"</textarea></td></tr>
		<tr><td></td><td align=right><input type=submit value=만들기><input type=button value=취소 onclick=\"javascript:history.go(-1);\"></td></tr></table>
		</form>";
}
//여기까지 새 파일 생성 페이지

elseif($action=="fmake_check"){
	$filename=trim($filename);
	check_name($filename, "파일");
	$content = htmlspecialchars($content);
	$rdir=opendir($dir);
	while ($list=readdir($rdir)){
		if (strtolower($filename)==strtolower($list)){
			check_make("같은 이름의 파일이 있습니다. 덮어쓰시겠습니까?");
			$exists=1;
		}
	}
	if(!$exists){check_make("파일을 생성하시겠습니까?");}
}
elseif($action=="fdelete_check"){
	$filename=trim($filename);
	if(file_exists($dir."/".$filename)){
		unlink($dir."/".$filename);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	else{
		echo"<center><br>파일이 존재하지 않습니다";
	}
}
elseif($action=="rmdir_check"){
	$rmdir=trim($rmdir);
	if(file_exists($dir."/".$rmdir)){
		$ch=0;
		$rdir=opendir($dir."/".$rmdir);
		while($list = readdir($rdir)) {
			if ($list != "." && $list != ".."){	$ch++;}
		}
		closedir($rdir);
		if ($ch==0){
			rmdir($dir."/".$rmdir);
			echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
			exit;
		}
		else{
			echo"<script>alert(\"디렉토리를 비워야 삭제할 수 있습니다\"); history.go(-1);</script>";
		}
	}
	else{
		echo"<center><br>파일이 존재하지 않습니다";
	}
}
else{  // 여기부터 리스트페이지
	$filename=trim($filename);
	if($mode=="make"){
			$content =stripslashes(stripslashes($content));
			$fp=fopen($dir."/".trim($filename), w);
			fwrite($fp, $content);
			fclose($fp);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	if($mode=="delete"){
		unlink($dir."/".$filename);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}

	//여기부터 업로드 페이지
	if ($action=="upload"){
		if ($mode=="upload_ok"){
			$uploadfile = $dir."/".$_FILES[userfile][name];
			if($userfile) { // 파일을 업로드 했을 경우
				if($max_filesize<(filesize($_FILES[userfile][tmp_name]))){
					echo("<script>alert (\"$max_filesize 이상 업로드할 수 없습니다.\"); history.go(-1);</script>");
					exit;
				}
				if(file_exists($uploadfile)) { $exist=1;}  // 동일한 이름을 갖는 파일이 이미 존재하는지 검사
				if(!copy($_FILES[userfile][tmp_name], $uploadfile)) {
					echo("<script>alert (\"파일업로드에 실패했습니다\"); history.go(-1);</script>");
					exit;
				}else{
					if($exist){	
						echo("<script>alert (\"덮어 씌웠습니다\");</script><meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>");
					}else {
					echo("<br><script>alert (\"파일 전송 완료\");</script><meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>");
					}
				exit;
				}
			}
		}
		else{
		echo"<form enctype=multipart/form-data action=$ftp_file method=post><INPUT TYPE=hidden name=MAX_FILE_SIZE value=$max_filesize><input type=hidden name=dir value=\"$dir\"><input type=hidden name=action value=upload><input type=hidden name=mode value=upload_ok>
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td><input name=userfile type=\"file\" ></td>
		<td></td><td align=right><input type=submit value=업로드 width=60;></td></tr>
		</table></form>";
		}
	} //여기까지 업로드 페이지

// 새 디렉토리 생성 페이지
if ($action=="dmake"){
	if($mode=="dmake_ok"){
			check_name($newdname, "디렉토리");
			if(file_exists($dir."/".$newdname)){
			echo"<script>alert(\"같은이름의 디렉토리가 있습니다\"); history.go(-1);</script>";
			exit;
			}
		mkdir($dir."/".$newdname, 0777);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
		echo "
		<form method=post action=$ftp_file>
		<input type=hidden name=dir value=\"$dir\">
		<input name=action type=hidden value=dmake>
		<input name=mode type=hidden value=dmake_ok>
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td width=70 align=right>폴더이름 : &nbsp;</td><td><input type=text name=newdname style=width:130></td>
		<td></td><td align=right><input type=submit value=만들기></td></tr>
		</table>
		</form>";
}
//여기까지 새 디렉토리 생성 페이지

//여기부터 이름 변경 페이지
if($action=="rename"){
	if ($mode=="rename_ok"){
		if(file_exists($dir."/".$newname) && ($dir."/".$filename)!=($dir."/".$newname) ){
			echo"<script>alert(\"같은이름의 파일이 있습니다\"); history.go(-1);</script>";
			exit;
		}
		rename($dir."/".$filename, $dir."/".$newname);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	else{
		echo "
		<form method=post action=$ftp_file>
		<input name=action type=hidden value=rename>
		<input type=hidden name=filename value=\"$filename\">
		<input name=mode type=hidden value=rename_ok>
		<input type=hidden name=dir value=\"$dir\">
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td width=70 align=right>바꿀이름 : &nbsp;</td><td><input type=text name=newname style=width:130 value=\"$filename\"></td>
		<td></td><td align=right><input type=submit value=바꾸기></td></tr>
		</table>
		</form>";
	}
}
//여기까지 이름변경 페이지

//리스트 헤드
	echo"<table align=center  cellpadding=0 cellspacing=0>
	</td></tr>
	<tr height=22><td colspan=3 ><span style=font-size:8pt; font-family:tahoma;line-height:100%;><font face=tahoma><b>$dir/</a></td><td colspan=4 align=right>남은 용량 : $remain_size</td></tr>
	<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
	<tr height=22>
		<td width=25 align=center>*</td>
		<td width=190 align=center>이 &nbsp;&nbsp;&nbsp;름</td>
		<td width=50 align=right>크 기</td>
		<td width=80 align=center>바뀐날짜</td>
		<td width=20 align=center>E</td>
		<td width=20 align=center>R</td>
		<td width=20 align=center>D</td>
		</tr>
	<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
	";
//여기까지 리스트헤드

// 디렉토리 목록
	$rdir=opendir("$dir");
	while($list_dir=readdir($rdir)){
			if (is_dir($dir."/".$list_dir) && $list_dir!="." && realpath($dir."/".$list_dir)!=($pwd."\\".$icon_url)){
			$filesize = ceil(filesize($dir."/".$list_dir)/1024) . "KB";
			$lasttime =  date("n/d H:i", filemtime($dir."/".$list_dir));
			echo"<tr height=21 bgcolor=f5f5f5>
			<td width=25 align=center><img src=$icon_url/ico_folder.gif></td>
			<td width=190><div class=name>";
			$gouplevel=explode("/", $dir);
			if (($dir."/".$list_dir) == "./.."){echo "";}
			elseif($list_dir == ".."){				 // ".."일 경우 $dir에서 마지막 디렉토리 요소 삭제
				$goupdir=".";
				for($cnt=1;$cnt<(sizeof($gouplevel)-1);$cnt++){
					$goupdir = $goupdir."/".$gouplevel[$cnt];
				}
				echo"<a href=\"$ftp_file?dir=$goupdir\">";
			}											//여기까지
			else{echo"<a href=\"$ftp_file?dir=$dir/$list_dir\">";}
			echo"$list_dir</a></td>
			<td width=50 align=right>　</td>
			<td width=80 align=center><font color=444444>";
			if ($list_dir == ".."){echo "　";}else{echo"$lasttime";}
			echo"
			</td><td width=20 align=center>　</td>
			<td width=20 align=center>";
			if ($list_dir == ".."){echo "　";}else{echo"<a href=\"$ftp_file?action=rename&filename=$list_dir&dir=$dir\"><img src=$icon_url/but_rename.gif border=0 alt=이름바꾸기></a>";}
			echo"
			</td><td width=20 align=center>";
			if ($list_dir == ".."){echo "　";}else{echo"<a href=\"$ftp_file?action=rmdir_check&rmdir=$list_dir&dir=$dir\"  onclick=\"return confirm('$list_dir 디렉토리를 삭제하시겠습니까?')\"><img src=$icon_url/but_delete.gif border=0></a>";}
			echo"
			</td></tr>
			<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
				";
			}
	}
//여기까지 디렉토리 목록

	//파일 목록
	$rdir=opendir("$dir");
	while($list=readdir($rdir)){
			//파일 확장자별 아이콘
			$filetype = explode(".",  $list);
			$filetype = $filetype[sizeof($filetype)-1];
			switch (strtolower($filetype)){
				case "htm":
					$fileicon="ico_html.gif";
					break;
				case "html":
					$fileicon="ico_html.gif";
					break;
				case "php":
					$fileicon="ico_php.gif";
					break;
				case "gif":
					$fileicon="ico_gif.gif";
					break;
				case "jpg":
					$fileicon="ico_jpg.gif";
					break;
				case "bmp":
					$fileicon="ico_bmp.gif";
					break;
				case "png":
					$fileicon="ico_png.gif";
					break;
				case "js":
					$fileicon="ico_js.gif";
					break;
				case "swf":
					$fileicon="ico_swf.gif";
					break;
				case "fla":
					$fileicon="ico_fla.gif";
					break;
				case "txt":
					$fileicon="ico_txt.gif";
					break;
				case "doc":
					$fileicon="ico_doc.gif";
					break;
				case "mid":
					$fileicon="ico_mid.gif";
					break;
				case "mp3":
					$fileicon="ico_mp3.gif";
					break;
				case "alz":
					$fileicon="ico_alz.gif";
					break;
				case "zip":
					$fileicon="ico_zip.gif";
					break;
				case "rar":
					$fileicon="ico_rar.gif";
					break;
				case "exe":
					$fileicon="ico_exe.gif";
					break;
				case "cgi":
					$fileicon="ico_cgi.gif";
					break;
				case "xls":
					$fileicon="ico_xls.gif";
					break;
				case "ttf":
					$fileicon="ico_ttf.gif";
					break;
				case "css":
					$fileicon="ico_css.gif";
					break;
				default:
					$fileicon="ico_default.gif";
					break;
			}
			//여기까지 아이콘설정

			if($list!="." && $list!= ".." && realpath($dir."/".$list)!= realpath($pwd."/".$ftp_file) && !is_dir($dir."/".$list)){
			$filesize = number_format(filesize($dir."/".$list)/1024);
			$sum_size= $sum_size+$filesize;
			$sum_obj++;
			$lasttime =  date("n/d H:i", filemtime($dir."/".$list));
			echo"<tr height=21>
			<td width=25 align=center><img src=$icon_url/$fileicon></td>
			<td width=190><div class=name><a href=\"$dir/$list\" target=_blank>$list</a></td>
			<td width=50 align=right><font color=444444>".$filesize."KB</td>
			<td width=80 align=center><font color=444444>$lasttime</td>
			<td width=20 align=center><a href=\"$ftp_file?action=fmake&filename=$list&mode=modify&dir=$dir\"><img src=$icon_url/but_edit.gif border=0 alt=편집></a></td>
			<td width=20 align=center><a href=\"$ftp_file?action=rename&filename=$list&dir=$dir\"><img src=$icon_url/but_rename.gif border=0 alt=이름바꾸기></a>
			</td><td width=20 align=center><a href=\"$ftp_file?action=fdelete_check&filename=$list&dir=$dir\" onclick=\"return confirm('$list 파일을 삭제하시겠습니까?')\"><img src=$icon_url/but_delete.gif border=0 alt=삭제></a>
			</td></tr>
			<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
			";
		}
	}
	//여기까지 파일목록
	echo"</table>";
	echo "<table width=405 border=0><tr><td align=right><span style=font-size:8pt;color:red;>".number_format($sum_obj)."</span><span style=font-size:7pt;> Files &nbsp;<span style=font-size:8pt;color:red;>".number_format($sum_size)."</span><span style=font-size:7pt;> KB</td></tr></table>";
}
// 여기부터 저작권. 삭제금지
echo"<table border=0 height=40><tr><td align=center valign=bottom><span style=font-size:8pt;font-family:tahoma;>Copyright <a href=http://punchto.dothome.co.kr target=_blank>Lepas</a> All Rights Reserved. <br></td></tr></table>";
// 저작권. 삭제금지
?>