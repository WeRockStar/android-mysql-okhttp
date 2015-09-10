#### build.gradle
```java
compile 'com.squareup.okhttp:okhttp:2.5.0'
```
#### PHP Basic
```php
<?php
include "connnection.php";
if (isset($_POST['txtName'])) {
    $txtName = $_POST['txtName'];
    $txtTel = $_POST['txtTel'];
    $txtMajor = $_POST['txtMajor'];

    $sql = "INSERT INTO student(name , tel , major) VALUES ('$txtName' , '$txtTel' , '$txtMajor')";
    $result = mysqli_query($conn , $sql);

    $respone = array();
    if(mysqli_affected_rows($conn) > 0)
        $respone['success'] = "1";
    else
        $respone['success'] = "0";

    echo json_encode($respone);
}
?>
```