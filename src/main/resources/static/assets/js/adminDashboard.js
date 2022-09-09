let studentCourse = new Chart(chartOne, {
    type: 'bar', // bar, horizontalBar, pie ...
    data: {
        labels: ["Basic Java", "Web Java", "React", "OJT", "Angular"],
        datasets: [{
            label: 'Attendance',
            data: [20, 23, 32, 15, 20], // number of news per reporter
            // backgroundColor: [...color],
            backgroundColor: 'lightblue',
            borderWidth: 1, // boder thickness of the bar
            borderColor: '#777',
            hoverBorderWidth: 2,
            hoverBorderColor: 'black'
        }],
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: '',
                font: {
                    size: 25
                }
            },
            legend: {
                // display: false, // hide label
                position: 'right', // move label to right side of chart
            },
            tooltips: {
                enabled: false
            }
        },
        layout: {
            padding: {
                left: 50,
                right: 0,
                bottom: 0,
                top: 0
            }
        },
    }
});

let totalMember = new Chart(chartTwo, {
    type: 'doughnut', // bar, horizontalBar, pie ...
    data: {
        labels: ["Java Batch 01", "Java Batch 02", " Java Batch 03"],
        datasets: [{
            // label: 'Number of News',
            data: [100, 10, 5], // number of news per reporter
            // backgroundColor: [...color],
            backgroundColor: ['#EC6B56', '#FFC154', '#47B39C'],
            borderWidth: 1, // boder thickness of the bar
            borderColor: '#777',
            hoverBorderWidth: 2,
            hoverBorderColor: 'black'
        }],
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: '',
                font: {
                    size: 25
                }
            },
            legend: {
                // display: false, // hide label
                position: 'right', // move label to right side of chart
            },
            tooltips: {
                enabled: false
            }
        },
        layout: {
            padding: {
                left: 50,
                right: 0,
                bottom: 0,
                top: 0
            }
        },
    }
});