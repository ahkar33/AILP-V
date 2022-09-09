
const app = Vue.createApp({
    data() {
        return {
            teacherId: '',
            courseId: '',
            examId: '',
            batchList: [],
        }
    },
    methods: {
    },
    mounted() {
        this.teacherId = document.getElementById('teacherId').value;
        this.courseId = document.getElementById('courseId').value;
        this.examId = document.getElementById('examId').value;
        axios
            .get(`http://localhost:8080/api/teacher/getTeacherBatchListByTeacherIdAndCourseId/${this.teacherId}/${this.courseId}`)
            .then(res => {
                this.batchList = [...res.data];
                console.log(this.batchList);
            })
            .catch(error => console.log(error));
    }
})
app.mount('#app');