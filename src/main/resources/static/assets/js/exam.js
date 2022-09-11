
const app = Vue.createApp({
    data() {
        return {
            isStarted: false,
            startDateTime: '',
            studentId: '',
            bheId: '',
            examId: '',
            bhe: {},
            exam: {},
            studentHasExam: {},
            studentAnswerList: [],
            studentScore: 0,
            // for counter
            displayDays: 0,
            displayHours: 0,
            displayMinutes: 0,
            displaySeconds: 0,
            isExamEnd: false,
            isLoaded: false,
            isSubmitted: false,
        }
    },
    computed: {
        _seconds: () => 1000,
        _minutes() {
            return this._seconds * 60;
        },
        _hours() {
            return this._minutes * 60;
        },
        _days() {
            return this._hours * 24;
        }
    },
    methods: {
        formatNum: num => (num < 10 ? '0' + num : num),
        showExamStartTime() {
            const timer = setInterval(() => {
                const now = new Date();
                const end = new Date(this.bhe.endDateTime);
                const distance = end.getTime() - now.getTime();
                if (distance <= 0) {
                    clearInterval(timer);
                    if (this.studentAnswerList.length > 0) {
                        this.handleSubmit();
                    }
                    this.isExamEnd = true;
                    this.isLoaded = true;
                    return;
                }
                const days = Math.floor((distance / this._days));
                const hours = Math.floor((distance % this._days) / this._hours);
                const minutes = Math.floor((distance % this._hours) / this._minutes);
                const seconds = Math.floor((distance % this._minutes) / this._seconds);
                this.displayMinutes = this.formatNum(minutes);
                this.displaySeconds = this.formatNum(seconds);
                this.displayHours = this.formatNum(hours);
                this.displayDays = this.formatNum(days);
                this.isLoaded = true;
            }, 1000)
        },
        formatDateTime(date) {
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var ampm = hours >= 12 ? 'pm' : 'am';
            hours = hours % 12;
            hours = hours ? hours : 12; // the hour '0' should be '12'
            minutes = minutes < 10 ? '0' + minutes : minutes;
            var strTime = hours + ':' + minutes + ' ' + ampm;
            return (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;
        },
        getBheData() {
            axios
                .get(`http://localhost:8080/api/exam/getBheById/${this.bheId}`)
                .then(res => {
                    this.bhe = { ...res.data };
                    this.exam = { ...this.bhe.bheExam };

                    this.startDateTime = this.formatDateTime(new Date(this.bhe.startDateTime));
                    const startDate = setInterval(() => {
                        let remainingTimeToStart = new Date(this.bhe.startDateTime).getTime() - new Date().getTime();
                        if (remainingTimeToStart <= 0) {
                            this.isStarted = true;
                            clearInterval(startDate);
                        }
                    }, 1000);

                    let remainingTime = new Date(this.bhe.endDateTime).getTime() - new Date().getTime();
                    if (remainingTime <= 0) {
                        this.isExamEnd = true;
                    }
                    this.isLoading = false;
                })
                .catch(error => console.log(error));
        },
        handleSubmit() {
            let rightAnswerList = [];
            this.exam.questionList.forEach(question => {
                rightAnswerList = [...rightAnswerList, [question.rightAnswer, question.mark]];
            });
            for (let i = 0; i < rightAnswerList.length; i++) {
                if (rightAnswerList[i][0] == this.studentAnswerList[i]) {
                    this.studentScore = this.studentScore + rightAnswerList[i][1];
                }
            }
            axios.post(`http://localhost:8080/api/exam/addStudentHasExam`, null, {
                params: {
                    studentId: this.studentId,
                    examId: this.examId,
                    score: this.studentScore
                }
            });
            this.isSubmitted = true;
        },
        getStudentHasExam() {
            axios
                .get(`http://localhost:8080/api/exam/getStudentHasExam/${this.studentId}/${this.examId}`)
                .then(res => {
                    this.studentHasExam = { ...res.data };
                    if (res.data != '') {
                        this.isSubmitted = true;
                        this.studentScore = this.studentHasExam.score;
                    }
                })
                .catch(error => console.log(error));
        }
    },
    mounted() {
        this.studentId = document.getElementById('studentId').value;
        this.bheId = document.getElementById('bheId').value;
        this.examId = document.getElementById('examId').value;
        this.getStudentHasExam();
        this.getBheData();
        this.showExamStartTime();
    }
})
app.mount('#app');