echo "Downloading main JAR..." &&
wget -q "https://github.com/theapache64/gilfoyle/releases/latest/download/gilfoyle.main.jar" -O "gilfoyle.main.jar" --show-progress &&

echo "Moving files to ~/.gilfoyle" &&

mkdir -p ~/.gilfoyle &&
mv gilfoyle.main.jar ~/.gilfoyle/gilfoyle.main.jar

echo "Installing..." &&
echo "\nalias gilfoyle='java -jar ~/.gilfoyle/gilfoyle.main.jar'" >> ~/.bash_aliases &&

echo "Done"