const alertSuccess = () => {
    Swal.fire(
        'Successfully Edited!', 
        ' ',
        'success'
    ).then(() => {
            window.location = `http://localhost:8080/admin/batch-table`;    
    });
}

const handleReset = (userId) => {
    Swal.fire({
        title: 'Are you sure want to reset password?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, reset it!'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = `http://localhost:8080/admin/changePassword/${userId}`;
        }
    })
}

$(document).ready(function(){
    let successMsg = document.getElementById("successMsg").value;
    if(successMsg){
        alertSuccess();
    }
});




