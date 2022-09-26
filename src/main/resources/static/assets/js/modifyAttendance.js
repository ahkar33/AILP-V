
const app = Vue.createApp({
    data() {
        return {
            batchId: 'Choose One Batch',
            teacherId: '',
            studentList: [],
            date: null
        }
    },
    methods: {
        handleSubmit() {
            if (this.date == null) {
                Swal.fire(
                    'Please Select Date!',
                    '',
                    'info'
                )
            }
            else {
                let data = [];
                this.studentList.forEach(student => {
                    data = [...data, { studentId: student.id, date: this.date, status: student.status }];
                });
                axios
                    .post('http://localhost:8080/api/student/modifyAttendance', data)
                    .then(() => {
                        Swal.fire(
                            'Successfully Uploaded!',
                            '',
                            'success'
                        )
                    })
                    .catch(error => console.log(error));
            }
        }
    },
    watch: {
        batchId: function (newBatchId, oldBatchId) {
            let batchId = newBatchId;
            if (batchId !== 'Choose One Batch') {
                axios
                    .get(`http://localhost:8080/api/student/getStudentListByBatchId/${batchId}`)
                    .then(res => {
                        this.studentList = [...res.data];
                    })
                    .catch(error => console.log(error));
            }
        },
    },
    mounted() {
        window.that = this;
        this.teacherId = document.getElementById('teacherId').value;
        axios
            .get(`http://localhost:8080/api/teacher/getStudentListByTeacherId/${this.teacherId}`)
            .then(res => {
                this.studentList = [...res.data];
                setTimeout(() => {
                    $("#dataTable").DataTable({
                        scrollX: true
                    });
                });
            })
            .catch(error => console.log(error));

        // $('#dataTable').on('search.dt', function () {
        //     let value = $('.dataTables_filter input').val();
        //     console.log('search');
        //     that.batchId = 'Choose One Batch';
        // });
    }
})
app.mount('#content');




