import { Line } from 'react-chartjs-2'
import { Chart, registerables } from 'chart.js'
Chart.register(...registerables)

const LineGraphics = ({ title, labels, datasetsLabels, data, year }) => {

    const newGraph = {
        labels: labels,
        datasets: [
          {
            label: datasetsLabels,
            backgroundColor: [
              'Indigo'
            ],
            fill: false,
            lineTension: 0,
            borderColor: 'rgba(0,0,0,1)',
            borderWidth: 2,
            data: data
          },
        ],
      }


    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <h2>{title}</h2>
            <h3>{year}</h3>
            <Line
            data={newGraph}
            options={{
                title: {
                    display: true,
                    text: title,
                    fontSize: 20,
                },
                legend: {
                    display: true,
                    position: 'right',
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }}
            />
        </div>
    )
}

export default LineGraphics;