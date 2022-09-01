const app = Vue.createApp({
    data() {
        return {
            lastMessageCount: null,
            audio: new Audio('/assets/mp3/noti.mp3'),
            userList: [],
            isMute: null,
            isFirstTime: true,
            isEmpty: null,
            messageListLength: '',
            batchId: '',
            userId: '',
            userName: '',
            message: '',
            messageList: [],
            oldMessagesCount: 0
        }
    },
    methods: {
        playNoti() {
            this.audio.play();
        },
        toggleMute() {
            let data = { id: this.userId, isMute: !this.isMute };
            axios
                .post('http://localhost:8080/api/user/toggleMute/', data)
                .then(() => {
                    console.log("toggle mute success");
                })
                .catch(error => console.log(error));
        },
        isToday(date) {
            let today = new Date();
            return today.toLocaleDateString() == date.substring(0, 9) ? true : false;
        },
        isYesterday(date) {
            let today = new Date();
            let yesterday = new Date();
            yesterday.setDate(today.getDate() - 1);
            return yesterday.toLocaleDateString() == date.substring(0, 9) ? true : false;
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
        getUserList() {
            axios
                .get('http://localhost:8080/api/user/getUserList')
                .then(res => {
                    this.userList = [...res.data];
                    this.userList.forEach(user => {
                        if (user.id == this.userId) {
                            this.isMute = user.isMute;
                        }
                    });
                })
                .catch(error => console.log(error));
        },
        getMessagesCount() {
            axios
                .get(`http://localhost:8080/api/message/countMessagesByBatchId/${this.batchId}`)
                .then(res => this.oldMessagesCount = res.data)
                .catch(error => console.log(error));
        },
        getLastMessageCount() {
            axios
                .get(`http://localhost:8080/api/message/countMessagesByBatchId/${this.batchId}`)
                .then(res => {
                    if (res.data != this.lastMessageCount) {
                        this.getAllMessages();
                    }
                    this.lastMessageCount = res.data;
                })
                .catch(error => console.log(error));
        },
        sendOldMesssagesCount() {
            let count = this.oldMessagesCount;
            axios
                .post(`http://localhost:8080/api/message/sendReadMessagesCount?count=${count}`)
                .catch(error => console.log(error));
        },
        getAllMessages() {
            axios.
                get(`http://localhost:8080/api/message/getMessagesByBatchId/${this.batchId}`)
                .then(res => {
                    this.messageList = [...res.data];
                    this.messageList = this.messageList.map(msg => {
                        let resDateTime = msg.dateTime;
                        let shortTime = resDateTime.substring(11, 15) + " " + resDateTime.slice(-2);
                        let longTime = resDateTime.substring(11, 16) + " " + resDateTime.slice(-2);
                        if (this.isToday(resDateTime)) {
                            var dateTime = (resDateTime.length == 21) ? "Today at " + shortTime : "Today at " + longTime;
                        } else if (this.isYesterday(resDateTime)) {
                            var dateTime = (resDateTime.length == 21) ? "Yesterday at " + shortTime : "Yesterday at " + longTime;
                        } else {
                            var dateTime = resDateTime.substring(0, 9);
                        }
                        return { ...msg, dateTime: dateTime, isHover: false };
                    });
                    let messageLength = this.messageList.length;
                    if (this.messageListLength < messageLength) {
                        if (this.messageList.at(-1).messageUser.id !== this.userId && !this.isMute && !this.isFirstTime) {
                            this.playNoti();
                            console.log("play")
                        }
                        setTimeout(() => {
                            let objDiv = document.getElementById("messages");
                            objDiv.scrollTop = objDiv.scrollHeight;
                        }, 0);
                    }
                    this.isFirstTime = false;
                    this.messageListLength = messageLength;
                })
                .catch(error => console.log(error));
        },
        deleteMessage(messageId) {
            axios.
                get(`http://localhost:8080/api/message/deleteMessageById/${messageId}`)
                .then(() => console.log("deleted message"))
                .catch(error => console.log(error));
        },
    },
    mounted() {
        window.that = this;
        this.batchId = document.getElementById('batchId').value;
        this.userId = document.getElementById('userId').value;
        this.userName = document.getElementById('userName').value;
        this.getAllMessages();
        setInterval(() => {
            this.getUserList();
            this.getLastMessageCount();
        }, 100);
        setInterval(() => {
            this.getMessagesCount();
            this.sendOldMesssagesCount();
        }, 1000);
        $("#inputMessage").emojioneArea({
            events: {
                keyup: function (editor, event) {
                    if (event.which == 13) {
                        document.activeElement.blur();
                        window.that.handleSend();
                        $("#inputMessage").data("emojioneArea").editor.focus();
                    }
                },
            }
        });
    },
})
app.mount('#app');