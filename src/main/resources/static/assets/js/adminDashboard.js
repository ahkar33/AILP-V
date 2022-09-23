// function hexToRgbA(hex) {
//     var c;
//     if (/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)) {
//         c = hex.substring(1).split('');
//         if (c.length == 3) {
//             c = [c[0], c[0], c[1], c[1], c[2], c[2]];
//         }
//         c = '0x' + c.join('');
//         return 'rgba(' + [(c >> 16) & 255, (c >> 8) & 255, c & 255].join(',') + ',0.4)';
//     }
//     throw new Error('Bad Hex');
// }

// let rgbaList = [];
// colors.forEach(color => {
//     rgbaList.push(hexToRgbA(color));
// });


const colorListOne = ["#EC6B56", "#FFC154", "#47B39C", "#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477", "#66aa00", "#b82e2e", "#316395", "#3366cc", "#994499", "#22aa99", "#aaaa11", "#6633cc", "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac", "#b77322", "#16d620", "#b91383", "#f4359e", "#9c5935", "#a9c413", "#2a778d", "#668d1c", "#bea413", "#0c5922", "#743411"];
const colorListTwo = [
    'rgba(236,107,86,0.4)', 'rgba(255,193,84,0.4)',
    'rgba(71,179,156,0.4)', 'rgba(51,102,204,0.4)',
    'rgba(220,57,18,0.4)', 'rgba(255,153,0,0.4)',
    'rgba(16,150,24,0.4)', 'rgba(153,0,153,0.4)',
    'rgba(0,153,198,0.4)', 'rgba(221,68,119,0.4)',
    'rgba(102,170,0,0.4)', 'rgba(184,46,46,0.4)',
    'rgba(49,99,149,0.4)', 'rgba(51,102,204,0.4)',
    'rgba(153,68,153,0.4)', 'rgba(34,170,153,0.4)',
    'rgba(170,170,17,0.4)', 'rgba(102,51,204,0.4)',
    'rgba(230,115,0,0.4)', 'rgba(139,7,7,0.4)',
    'rgba(101,16,103,0.4)', 'rgba(50,146,98,0.4)',
    'rgba(85,116,166,0.4)', 'rgba(59,62,172,0.4)',
    'rgba(183,115,34,0.4)', 'rgba(22,214,32,0.4)',
    'rgba(185,19,131,0.4)', 'rgba(244,53,158,0.4)',
    'rgba(156,89,53,0.4)', 'rgba(169,196,19,0.4)',
    'rgba(42,119,141,0.4)', 'rgba(102,141,28,0.4)',
    'rgba(190,164,19,0.4)', 'rgba(12,89,34,0.4)',
    'rgba(116,52,17,0.4)'
];

// -----------------------------------------------------------------------------------------------------------------

let attendanceBatchList = [];
let attendancePercentList = [];

axios.get('http://localhost:8080/api/admin/getBatchAttendance')
    .then(res => {
        attendanceBatchList = Object.keys(res.data);
        attendancePercentList = Object.values(res.data);
    })
    .then(() => {
        let batchAttendance = new Chart(chartOne, {
            type: 'bar', // bar, horizontalBar, pie ...
            data: {
                labels: attendanceBatchList,
                datasets: [{
                    label: 'Attendance Percentage',
                    data: attendancePercentList,
                    backgroundColor: [...colorListTwo],
                    // backgroundColor: 'lightblue',
                    borderWidth: 1, // boder thickness of the bar
                    borderColor: '#777',
                    hoverBorderWidth: 2,
                    hoverBorderColor: 'black'
                }],
            },
            options: {
                plugins: {
                    legend: {
                        // display: false, // hide label
                        position: 'top', // move label to right side of chart
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
    })
    .catch(error => console.log(error.message));

// -----------------------------------------------------------------------------------------------------------------
let countCourseList = [];
let countStudentList = [];


axios.get('http://localhost:8080/api/admin/getStudentCountByCourse')
    .then((res) => {
        countCourseList = Object.keys(res.data);
        countStudentList = Object.values(res.data);
    }).then(() => {     
        let totalStudents = new Chart(chartTwo, {
            type: 'doughnut', // bar, horizontalBar, pie ...
            data: {
                labels: countCourseList,
                datasets: [{
                    label: 'Total Students',
                    data: countStudentList, // number of news per reporter
                    backgroundColor: [...colorListOne],
                    borderWidth: 1, // boder thickness of the bar
                    borderColor: '#777',
                    hoverBorderWidth: 2,
                    hoverBorderColor: 'black'
                }],
            },
            options: {
                plugins: {
                    legend: {
                        // display: false, // hide label
                        position: 'top', // move label to right side of chart
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
    }) 