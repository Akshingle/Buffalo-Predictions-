from PIL import Image, ImageDraw
import os

# Create directories if they don't exist
dirs = [
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-mdpi',
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-hdpi',
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xhdpi',
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xxhdpi',
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xxxhdpi'
]

# Sizes for different densities
sizes = {
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-mdpi': 48,
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-hdpi': 72,
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xhdpi': 96,
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xxhdpi': 144,
    'C:\\Users\\akshay.ingle\\Desktop\\APP\\app\\src\\main\\res\\mipmap-xxxhdpi': 192
}

# Create a simple buffalo milk predictor icon (orange circle with simple design)
for dir_path, size in sizes.items():
    os.makedirs(dir_path, exist_ok=True)
    
    # Create image with orange background
    img = Image.new('RGBA', (size, size), (255, 152, 0, 255))  # Orange color
    draw = ImageDraw.Draw(img)
    
    # Draw a simple white circle inside (representing milk/udder)
    margin = size // 4
    draw.ellipse([margin, margin, size - margin, size - margin], fill=(255, 255, 255, 255))
    
    # Save the image
    img.save(os.path.join(dir_path, 'ic_launcher.png'))
    print(f'Created ic_launcher.png at {dir_path}')

print('All launcher icons created successfully!')
