const app = Vue.createApp({
    data() {
        return {
            isEmpty: null,
            messageListLength: '',
            batchId: '',
            userId: '',
            userName: '',
            message: '',
            messageList: []
        }
    },
    methods: {
        isToday(date) {
            let today = new Date();
            if (today.toLocaleDateString() == date.substring(0, 9)) {
                return true;
            }
            return false;
        },
        isYesterday(date) {
            let today = new Date();
            let yesterday = new Date();
            yesterday.setDate(today.getDate() - 1);
            if (yesterday.toLocaleDateString() == date.substring(0, 9)) {
                return true;
            }
            return false;
        },
        handleSend() {
            this.message = document.getElementById('inputMessage').value;
            this.isEmpty = false;
            if (this.message.replace(/\s/g, "").length == 0) {
                this.isEmpty = true;
            }
            if (!this.isEmpty) {
                let data = {
                    message: this.message,
                    userId: this.userId,
                    batchId: this.batchId,
                    dateTime: new Date().toLocaleString()
                }

                $("#inputMessage").data("emojioneArea").setText('');

                axios
                    .post('http://localhost:8080/api/message/addMessage/', data)
                    .then(() => {
                        console.log("success");
                        this.getAllMessages();
                    })
                    .catch(error => console.log(error));
            }
        },
        getAllMessages() {
            axios.
                get(`http://localhost:8080/api/message/getMessagesByBatchId/${this.batchId}`)
                .then(res => {
                    this.messageList = [...res.data];
                    this.messageList = this.messageList.map(msg => {
                        let resDateTime = msg.dateTime;
                        if (this.isToday(resDateTime)) {
                            if (resDateTime.length == 21) {
                                var dateTime = "Today at " + resDateTime.substring(11, 15) + " " + resDateTime.slice(-2);
                            } else {
                                var dateTime = "Today at " + resDateTime.substring(11, 16) + " " + resDateTime.slice(-2);
                            }
                        } else if (this.isYesterday(resDateTime)) {
                            if (resDateTime.length == 21) {
                                var dateTime = "Yesterday at " + resDateTime.substring(11, 15) + " " + resDateTime.slice(-2);
                            } else {
                                var dateTime = "Yesterday at " + resDateTime.substring(11, 16) + " " + resDateTime.slice(-2);
                            }
                        } else {
                            var dateTime = resDateTime.substring(0, 9);
                        }
                        return { ...msg, dateTime: dateTime };
                    });
                    let messageLength = this.messageList.length
                    if (this.messageListLength < messageLength) {
                        setTimeout(() => {
                            let objDiv = document.getElementById("messages");
                            objDiv.scrollTop = objDiv.scrollHeight;
                        }, 0);
                    }
                    this.messageListLength = messageLength;
                })
                .catch(error => console.log(error));
        }
    },
    mounted() {
        $("#inputMessage").emojioneArea({
        });
        this.batchId = document.getElementById('batchId').value;
        this.userId = document.getElementById('userId').value;
        this.userName = document.getElementById('userName').value;
        setInterval(() => {
            this.getAllMessages();
        }, 1000);
    }
})
app.mount('#app');

