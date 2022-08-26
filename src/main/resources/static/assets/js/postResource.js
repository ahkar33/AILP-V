
const app = Vue.createApp({
    data() {
        return {
            batchId: 'Choose One Batch',
            date: '',
            resourceList: [],
            resourceId: [],
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
            } else if(this.resourceId.length == 0) {
                this.isEmpty = true;
                Swal.fire(
                    'Please Check At Least One Resource!',
                    '',
                    'info'
                )
            }

            if (!this.isEmpty) {
                let data = [];
                this.resourceId.forEach(id => {
                    data = [...data, {
                        bhrResourceId: id,
                        bhrBatchId: this.batchId,
                        schedule: this.date
                    }]
                });
                axios
                    .post(`http://localhost:8080/api/teacher/postResourceForBatch`, data)
                    .then(() => {
                        Swal.fire(
                            'Successfully Uploaded!',
                            '',
                            'success'
                        ).then(() => window.location = "http://localhost:8080/teacher/postResource");
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
                    .get(`http://localhost:8080/api/teacher/getResourceListByBatchId/${batchId}`)
                    .then(res => {
                        this.resourceList = [...res.data];
                    })
                    .catch(error => console.log(error));
            }
        }
    },
    mounted() {
        axios
            .get(`http://localhost:8080/api/teacher/getResourceListByTeacherId`)
            .then(res => {
                this.resourceList = [...res.data];
                setTimeout(() => {
                    $("#dataTable").DataTable({});
                });
            })
            .catch(error => console.log(error));
    }
})
app.mount('#app');



    // $('#dataTable').on('search.dt', function () {
    //     let value = $('.dataTables_filter input').val();
    //     console.log('search');
    //     that.batchId = 'Choose One Batch';
    // });

