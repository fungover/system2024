import React from 'react';

const TodayDate: React.FC = () => {
  const today = new Date();
  const options: Intl.DateTimeFormatOptions = {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  };
  
  const formattedDate = today.toLocaleDateString('sv-SE', options);

  return (
    <div className="text-lg font-medium">
      Idag är {formattedDate}
    </div>
  );
};

export default TodayDate;