
const app = Vue.createApp({
    data() {
        return {
            studentId: '',
            bheId: '',
            bhe: {},
            exam: {},
            studentAnswerList: []
        }
    },
    methods: {
        handleSubmit() {
            let rightAnswerList = [];
            let studentScore = 0;
            this.exam.questionList.forEach(question => {
                rightAnswerList = [...rightAnswerList, [question.rightAnswer, question.mark]];
            });
            for (let i = 0; i < rightAnswerList.length; i++) {
                if (rightAnswerList[i][0] == this.studentAnswerList[i]) {
                    studentScore = studentScore + rightAnswerList[i][1];
                }
            }
            console.log(studentScore);
        }
    },
    mounted() {
        this.studentId = document.getElementById('studentId').value;
        this.bheId = document.getElementById('bheId').value;
        axios
            .get(`http://localhost:8080/api/exam/getBheById/${this.bheId}`)
            .then(res => {
                this.bhe = { ...res.data };
                this.exam = { ...this.bhe.bheExam };
            })
            .catch(error => console.log(error));
    }
})
app.mount('#app');