<?php  
    
    $sid = $_POST["sid"];  
    $day = $_POST["day"];  
    $month = $_POST["month"]; 
    $year = $_POST["year"];
    
    
    
    // db details
    $user = "root";  
    $password = "ILgta77!@#";  
    $host ="localhost";  
    $db_name ="game"; 
    
    //put data
    $con = mysqli_connect($host,$user,$password,$db_name);  
    $sql = "insert into register values('".$sid."','".$day."','".$month."','".$year."');";  
    //$sql = "insert into register values('sanved33',22,2,1996);";  
    if(mysqli_query($con,$sql))  
    {  
        echo "Data inserted successfully....";  
    }  
    else   
    {  
        echo "some error occured";  
    }  
    mysqli_close($con);  
?> 
