#! /bin/bash

for dir in */ ; do
    if [[ ${dir:0:1} != "." ]]; then
      cd "$dir"
      (
        echo "Installing ${dir%*/} ..."
        mvn clean > /dev/null && mvn -U install > /dev/null
        echo "  ... ${dir%*/} correctly installed"
      ) &
      cd ..
    fi
done

wait
echo "Done"