import { Chart, registerables } from 'chart.js'
import { Doughnut } from 'react-chartjs-2'
import { useTranslation } from "react-i18next";
Chart.register(...registerables)

const CircleGraphics = ({ title,  year, labels, data }) => {

    const { t } = useTranslation('adminAnalytics');

    const CircleGarph = {
        labels: labels,
        datasets: [{
          label: title,
          data: data,
          backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)',
            'rgb(75, 192, 192)',
          ],
          hoverOffset: 4
        }]
      };

    const noAppointment = data.every(val => val === 0);

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <h2>{title}</h2>
            <h3>{year}</h3>
            {noAppointment ? (
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', width: '300px', height: '300px' }}>
                    <h3>{t('noAppointment')}</h3>
                </div>
            ) : (
                <div style={{ width: '300px', height: '300px' }}>
                    <Doughnut
                    data={CircleGarph}
                    options={{
                        title: {
                            display: true,
                            text: title,
                            fontSize: 20,
                        },
                        legend: {
                            display: true,
                            position: 'center',
                        },
                    }}
                    />
                </div>
            )}
        </div>
    )
}

export default CircleGraphics;