
const app = Vue.createApp({
    data() {
        return {
            studentId: '',
            bheId: '',
            bhe: {},
            exam: {},
            questionList: []
        }
    },
    methods: {
    },
    mounted() {
        this.studentId = document.getElementById('studentId').value;
        this.bheId = document.getElementById('bheId').value;
        axios
            .get(`http://localhost:8080/api/exam/getBheById/${this.bheId}`)
            .then(res => {
                this.bhe = {...res.data};
                this.exam = {...this.bhe.bheExam};
                this.questionList = [...this.exam.questionList];
            })
            .catch(error => console.log(error));
    }
})
app.mount('#app');