
const app = Vue.createApp({
    data() {
        return {
            batchId: 'Choose One Batch',
            date: '',
            videoList: [],
            videoId: [],
            isEmpty: false
        }
    },
    methods: {
        handleSubmit() {

            this.isEmpty = false;

            console.log(this.batchId);
            if (this.batchId === 'Choose One Batch' || this.batchId.length === 0) {
                this.isEmpty = true;
                Swal.fire(
                    'Please Choose Batch!',
                    '',
                    'info'
                )
            } else if (this.date.length === 0) {
                this.isEmpty = true;
                Swal.fire(
                    'Please Choose Date!',
                    '',
                    'info'
                )
            } else if(this.videoId.length == 0) {
                this.isEmpty = true;
                Swal.fire(
                    'Please Check At Least One Video!',
                    '',
                    'info'
                )
            }

            if (!this.isEmpty) {
                let data = [];
                this.videoId.forEach(id => {
                    data = [...data, {
                        bhvVideoId: id,
                        bhvBatchId: this.batchId,
                        schedule: this.date
                    }]
                });
                axios
                    .post(`http://localhost:8080/api/teacher/postVideoForBatch`, data)
                    .then(() => {
                        Swal.fire(
                            'Successfully Uploaded!',
                            '',
                            'success'
                        ).then(() => window.location = "http://localhost:8080/teacher/postVideo");
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
                    .get(`http://localhost:8080/api/teacher/getVideoListByBatchId/${batchId}`)
                    .then(res => {
                        this.videoList = [...res.data];
                    })
                    .catch(error => console.log(error));
            }
        }
    },
    mounted() {
        axios
            .get(`http://localhost:8080/api/teacher/getVideoListByTeacherId`)
            .then(res => {
                this.videoList = [...res.data];
                setTimeout(() => {
                    $("#dataTable").DataTable({
                        scrollX: true
                    });
                });
            })
            .catch(error => console.log(error));
    }
})
app.mount('#app');