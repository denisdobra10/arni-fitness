/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#AC0D0C',
        textcolor: '#ffffff'
      },
      boxShadow: {
        'spreaded': '0 10px 100px -15px rgba(0, 0, 0, 0.3)',
      },
    },
  },
  plugins: [],
}