
const app = Vue.createApp({
    data() {
        return {
            batchId: 'Choose One Batch',
            date: '',
            resourceList: [],
            resourceId: []
        }
    },
    methods: {
        handleSubmit() {
            let data = [];
            this.resourceId.forEach(id => {
                data = [...data, {
                    bhrResourceId: id,
                    bhrBatchId: this.batchId,
                    schedule: this.date
                }]
            });

            console.log(data);

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

