<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $title=$_POST['title'];
        $adex=$_POST['adex'];
	$youtubeid=$_POST['youtubeid'];
	$email=$_POST['email'];
	$longex=$_POST['longex'];
	$caution=$_POST['caution'];
	$startdate=$_POST['startdate'];
	$enddate=$_POST['enddate'];

        if(empty($title)){
            $errMSG = "타이틀을 입력하세요.";
        }
        else if(empty($adex)){
            $errMSG = "설명을 입력하세요.";
	}
	else if(empty($youtubeid)){
	    $errMSG = "유튜브 닉네임을 입력하세요.";
	}
	else if(empty($email)){
	    $errMSG = "이메일을 입력하세요.";
	}
	else if(empty($longex)){
	    $errMSG = "소개를 입력하세요.";
	}
	else if(empty($caution)){
	    $errMSG = "주의사항을 입력하세요.";
	}
	else if(empty($startdate)){
	    $errMSG = "첫번째 날짜를 입력하세요";
	}
	else if(empty($enddate)){
	    $errMSG = "마지막 날짜를 입력하세요";
	}

        if(!isset($errMSG)) 
        {
            try{ 
                $stmt = $con->prepare('INSERT INTO insertdata(title, adex, youtubeid, email, longex, caution, startdate, enddate) VALUES(:title, :adex, :youtubeid, :email, :longex, :caution, :startdate, :enddate)');
		$stmt->bindParam(':title', $title);
		$stmt->bindParam(':adex', $adex);
		$stmt->bindParam(':youtubeid', $youtubeid);
		$stmt->bindParam(':email', $email);
		$stmt->bindParam(':longex', $longex);
		$stmt->bindParam(':caution', $caution);
		$stmt->bindParam(':startdate', $startdate);
		$stmt->bindParam(':enddate', $enddate);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
		Title: <input type = "text" name = "title" />
		Adex: <input type = "text" name = "adex" />
		Youtubeid: <input type = "text" name = "youtubeid" />
		Email: <input type = "text" name = "email" />
		Longex: <input type = "textarea" name = "longex" />
		Caution: <input type = "textarea" name = "caution" />
		StartDate: <input type = "text" name = "startdate" />
		EndDate: <input type = "text" name = "enddate" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>


