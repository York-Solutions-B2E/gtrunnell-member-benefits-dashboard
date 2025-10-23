/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                tennis: '#B7E23F', // 👈 your custom tennis-ball green
            },
        },
    },
    plugins: [],
}
